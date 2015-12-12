/**
 * Created by artemvlasov on 08/12/15.
 */
$.validator.addMethod('regexpName', function(value, element, param) {
        return this.optional(element) || value.match(param);
    },
    'Name should not contains numbers');
$.validator.addMethod('regexpUsername', function(value, element, param) {
        return this.optional(element) || value.match(param);
    },
    'Username should contains characters from a-z, number and symbols space _ - .');
(function($,W,D)
{
    var JQUERY4U = {};

    JQUERY4U.UTIL =
    {
        setupFormValidation: function()
        {
            //form validation rules
            $("#tv-space-reg-form").validate({
                rules: {
                    name: {
                        maxlength: 50,
                        minlength: 6,
                        regexpName: "^[A-Za-z '.\-]{6,50}$"
                    },
                    username: {
                        minlength: 6,
                        maxlength: 100,
                        regexpUsername: "^[A-Za-z0-9\- ._]{6,100}$",
                        required: true
                    },
                    email: {
                        required: true,
                        minlength: 8,
                        maxlength: 100,
                        email: true
                    },
                    password: {
                        required: true,
                        minlength: 8,
                        maxlength: 128
                    }
                },
                messages: {
                    password: {
                        required: "Please provide an password",
                        minlength: "Your password must be at least 8 characters long",
                        maxlenght: "Your password must be not more than 128 characters long"
                    },
                    username :{
                        required: "Please provide an username",
                        minlength: "Your username must be at least 6 characters long",
                        maxlenght: "Your username must be not more than 100 characters long"
                    },
                    name: {
                        minlength: "Your name must be at least 6 characters long",
                        maxlenght: "Your name must be not more than 50 characters long"
                    },
                    email: {
                        required: "Please provide a email",
                        email: "Please enter a valid email address",
                        minlength: "Your email must be at least 8 characters long",
                        maxlength: "Your email must be not more than 100 characters long"
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