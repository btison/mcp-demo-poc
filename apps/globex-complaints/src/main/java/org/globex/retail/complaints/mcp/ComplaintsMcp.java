package org.globex.retail.complaints.mcp;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.logging.Log;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import org.globex.retail.complaints.model.ComplaintDto;
import org.globex.retail.complaints.model.CreateComplaintRequest;
import org.globex.retail.complaints.model.UpdateComplaintRequest;
import org.globex.retail.complaints.service.ComplaintService;

import java.util.Optional;

public class ComplaintsMcp {

    @Inject
    ComplaintService complaintService;

    @Inject
    HttpServerRequest request;

    @Tool(name = "create_complaint", description = "Create a product complaint")
    public String createComplaint(@ToolArg(description = "The order ID to which this complaint refers to") Long orderId,
                                  @ToolArg(description = "The productCode of the product to which this complaint refers to") String productCode,
                                  @ToolArg(description = "The issue type of the complaint. Accepted values: defect, missing_parts, wrong_item, performance, other ") String issueType,
                                  @ToolArg(description = "The severity of the issue or complaint. Accepted values: low, medium, high, or critical", required = false) String severity,
                                  @ToolArg(description = "The preferred resolution of the user for this complaint. Accepted values: refund, replacement, repair, other", required = false) String resolution,
                                  @ToolArg(description = "The description of the complaint") String description) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isEmpty()) {
            Log.error("Missing X-User-Id HTTP Header");
            throw new RuntimeException("Missing X-User-Id HTTP Header");
        }
        CreateComplaintRequest createComplaintRequest = new CreateComplaintRequest();
        createComplaintRequest.userId = userId;
        createComplaintRequest.orderId = orderId;
        createComplaintRequest.productCode = productCode;
        createComplaintRequest.issueType = issueType;
        createComplaintRequest.severity = severity;
        createComplaintRequest.complaint = description;
        createComplaintRequest.resolution = resolution;
        ComplaintDto complaintDTO = complaintService.createComplaint(createComplaintRequest);
        return String.valueOf(complaintDTO.id);
    }

    @Tool(name = "update_complaint", description = "Update an existing product complaint.")
    public String updateComplaint(@ToolArg(description = "the ID of the complaint") String id,
                                  @ToolArg(description = "The issue type of the complaint. Accepted values: defect, missing_parts, wrong_item, performance, other", required = false) String issueType,
                                  @ToolArg(description = "The severity of the issue or complaint. Accepted values: low, medium, high, or critical", required = false) String severity,
                                  @ToolArg(description = "The preferred resolution of the user for this complaint. Accepted values: refund, replacement, repair, other", required = false) String resolution,
                                  @ToolArg(description = "The description of the complaint", required = false) String description) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isEmpty()) {
            Log.error("Missing X-User-Id HTTP Header");
            throw new RuntimeException("Missing X-User-Id HTTP Header");
        }
        UpdateComplaintRequest updateComplaintRequest = new UpdateComplaintRequest();
        updateComplaintRequest.issueType = issueType;
        updateComplaintRequest.severity = severity;
        updateComplaintRequest.resolution = resolution;
        updateComplaintRequest.complaint = description;
        Optional<ComplaintDto> complaintDtoOptional = complaintService.updateComplaint(Long.valueOf(id), updateComplaintRequest);
        return complaintDtoOptional.map(dto -> String.valueOf(dto.id)).orElseThrow(() -> new RuntimeException("Complaint with id " + id + " not found"));
    }
}
