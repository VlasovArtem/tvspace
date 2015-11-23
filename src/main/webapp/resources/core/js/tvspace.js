/**
 * Created by artemvlasov on 22/11/15.
 */
function sayHello() {
    console.log($('input[type=date]').val());
    var data = $('form').serializeArray();
    for(var i = 0; i < data.length; i++) {
        console.log(data[i].value);
    }

}