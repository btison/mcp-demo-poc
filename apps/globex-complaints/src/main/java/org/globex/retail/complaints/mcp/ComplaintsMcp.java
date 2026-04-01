package org.globex.retail.complaints.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkiverse.mcp.server.ToolCallException;
import io.quarkus.logging.Log;
import io.vertx.core.http.HttpServerRequest;
import jakarta.inject.Inject;
import org.globex.retail.complaints.model.ComplaintDto;
import org.globex.retail.complaints.model.CreateComplaintRequest;
import org.globex.retail.complaints.model.UpdateComplaintRequest;
import org.globex.retail.complaints.service.ComplaintService;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ComplaintsMcp {

    @Inject
    ComplaintService complaintService;

    @Inject
    HttpServerRequest request;

    @Inject
    ObjectMapper objectMapper;

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
            throw new ToolCallException("Missing X-User-Id HTTP Header");
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
    public String updateComplaint(@ToolArg(description = "The ID of the complaint") String id,
                                  @ToolArg(description = "The issue type of the complaint. Accepted values: defect, missing_parts, wrong_item, performance, other", required = false) String issueType,
                                  @ToolArg(description = "The severity of the issue or complaint. Accepted values: low, medium, high, or critical", required = false) String severity,
                                  @ToolArg(description = "The preferred resolution of the user for this complaint. Accepted values: refund, replacement, repair, other", required = false) String resolution,
                                  @ToolArg(description = "The description of the complaint", required = false) String description) {
        String userId = request.getHeader("X-User-Id");
        if (userId == null || userId.isEmpty()) {
            Log.error("Missing X-User-Id HTTP Header");
            throw new ToolCallException("Missing X-User-Id HTTP Header");
        }
        UpdateComplaintRequest updateComplaintRequest = new UpdateComplaintRequest();
        updateComplaintRequest.issueType = issueType;
        updateComplaintRequest.severity = severity;
        updateComplaintRequest.resolution = resolution;
        updateComplaintRequest.complaint = description;
        Optional<ComplaintDto> complaintDtoOptional = complaintService.updateComplaint(Long.valueOf(id), updateComplaintRequest);
        return complaintDtoOptional.map(dto -> String.valueOf(dto.id)).orElseThrow(() -> new RuntimeException("Complaint with id " + id + " not found"));
    }

    @Tool(name = "get_complaint", description = "Get product complaints for a given product and a given period, sorted by severity. Returns a list of JSON objects.")
    public String getComplaintsByProduct(@ToolArg(description = "The product code") String productId,
                                         @ToolArg(description = "The start date of the period. Format 'yyyy-MM-dd") String startDate,
                                         @ToolArg(description = "The end date of the period. Format 'yyyy-MM-dd") String endDate) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startLocalDate = LocalDate.parse(startDate, dateFormatter);
        LocalDate endLocalDate = LocalDate.parse(endDate, dateFormatter);
        OffsetDateTime start = startLocalDate.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime end = endLocalDate.plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);
        List<ComplaintDto> complaintList = complaintService.findByProductCodeAndTimeRange(productId, start, end, true, null, null);
        try {
            return objectMapper.writeValueAsString(complaintList);
        } catch (JsonProcessingException e) {
            throw new ToolCallException("Failed to get complaint: " + e.getMessage(), e);
        }
    }
}
