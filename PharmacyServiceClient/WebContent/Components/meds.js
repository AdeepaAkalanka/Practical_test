$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});
// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidMedIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "MedsAPI",
		type : type,
		data : $("#formMed").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onMedSaveComplete(response.responseText, status);
		}
	});	
});

function onMedSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divMedsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidMedIDSave").val("");
	$("#formMed")[0].reset();
}

//DELETE=============================================================================
	$(document).on("click", ".btnRemove", function(event) {
		$.ajax({
			url : "MedsAPI",
			type : "DELETE",
			data : "med_id=" + $(this).data("medid"),
			dataType : "text",
			complete : function(response, status) {
				onMedDeleteComplete(response.responseText, status);
			}
		});
	});
	
	
	function onMedDeleteComplete(response, status) {
		if (status == "success") {
			var resultSet = JSON.parse(response);
			if (resultSet.status.trim() == "success") {
				$("#alertSuccess").text("Successfully deleted.");
				$("#alertSuccess").show();
				$("#divMedsGrid").html(resultSet.data);
			} else if (resultSet.status.trim() == "error") {
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while deleting.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while deleting..");
			$("#alertError").show();
		}
	}


// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidMedIDSave").val(
					$(this).closest("tr").find('#hidMedIDUpdate').val());
			$("#med_name").val($(this).closest("tr").find('td:eq(0)').text());
			$("#med_code").val($(this).closest("tr").find('td:eq(1)').text());
			$("#med_price").val($(this).closest("tr").find('td:eq(2)').text());
			$("#med_description").val($(this).closest("tr").find('td:eq(3)').text());
		});
// CLIENTMODEL=========================================================================
function validateItemForm() {
	// MEDICINENAME
	if ($("#med_name").val().trim() == "") {
		return "Insert Medicine Name.";
	}
	// MEDICINECODE
	if ($("#med_code").val().trim() == "") {
		return "Insert Medicine Code.";
	}
	// is numerical value
	var tmpCode = $("#med_code").val().trim();
	if (!$.isNumeric(tmpCode)) {
		return "Insert Only Numbers for Medicine Code.";
	}
	// PRICE-------------------------------
	if ($("#med_price").val().trim() == "") {
		return "Insert Price.";
	}
	// is numerical value
	var tmpPrice = $("#med_price").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Price.";
	}
	// DESCRIPTION------------------------
	if ($("#med_description").val().trim() == "") {
		return "Insert Description.";
	}
	
	return true;
}
