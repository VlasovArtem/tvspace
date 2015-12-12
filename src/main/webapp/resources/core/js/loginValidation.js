
/**
 * Created by artemvlasov on 09/12/15.
 */
(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#login-form").validate({
                rules: {
                    loginData: {
                        required: true
                    },
                    password: {
                        required: true
                    }
                },
                messages: {
                    password: {
                        required: "Please provide a password"
                    },
                    loginData :{
                        required: "Please provide a username"
                    }
                },
                submitHandler: function(form) {
                    form.submit();
                }
            });
        }
    };

    //when the dom has loaded setup form validation rules
    $(D).ready(function($) {
        JQUERY4U.UTIL.setupFormValidation();
    });

})(jQuery, window, document);