var options = { 
	beforeSubmit:  validate,  // pre-submit callback 
	success:       showResponse,  // post-submit callback 
	resetForm: true        // reset the form after successful submit 
}; 
				
$('#contact_form').ajaxForm(options); 
				
function showResponse(responseText, statusText){
	$('#success').animate({ opacity: "show" }, "fast")
}
				
function validate(formData, jqForm, options) {
	$("span.error").animate({ opacity: "hide" }, "slow");
			 
	var nameValue = $('input[name=Name]').fieldValue(); 
	var emailValue = $('input[name=Email]').fieldValue();
	var messageValue = $('textarea[name=Message]').fieldValue(); 
	
	var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
	var correct = true;
	
	if (!nameValue[0]) {
		$("span.error.name").animate({ opacity: "show" }, "slow");
		correct = false;
	}
	
	if (!emailValue[0]) {
		$("span.error.email").animate({ opacity: "show" }, "slow");
		correct = false;
	} else if(!emailReg.test(emailValue[0])) {
		$("span.error.email").animate({ opacity: "show" }, "slow");
		correct = false;
	}
	
	if (!messageValue[0]) {
		$("span.error.message").animate({ opacity: "show" }, "slow");
		correct = false;
	}
	
	if (!correct) {return false;}
} 	
						 
$("input#reset").click( function () { 
	$("span.error.name").animate({ opacity: "hide" }, "slow");
	$("span.error.email").animate({ opacity: "hide" }, "slow");
	$("span.error.message").animate({ opacity: "hide" }, "slow");
	$('#success').animate({ opacity: "hide" }, "fast")
});	