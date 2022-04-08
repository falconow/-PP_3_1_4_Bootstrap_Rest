$(document).ready(getusers());

function getusers() {
    $.ajax({
        url: '/admin/users',         /* Куда пойдет запрос */
        method: 'get',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        data: {},     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
            /* В переменной data содержится ответ от index.php. */
            data.forEach(function (element, key) {
                var tr = $("<tr></tr>")
                    .append('<th scope="row">' + element["id"] + '</th>')
                    .append('<td>' + element["firstName"] + '</td>')
                    .append('<td>' + element["lastName"] + '</td>')
                    .append('<td>' + element["age"] + '</td>')
                    .append('<td>' + element["email"] + '</td>')
                var role = '';
                element["roles"].forEach(function(element, key){
                    role = role + element + ' ';
                });
                tr.append('<td>' + role + '</td>')
                    .append('<td><button type="button" class="btn btn-info" data-toggle="modal">Edit</button></td>')
                    .append('<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#DeleteModal" data-whatever="'+ element["id"] +'">Delete</button></td>');
                $('#table').append(tr)
            })
        }
    });
}

$('#formNewUser').on('submit', function (e) {
    e.preventDefault();
    $.ajax({
        method: 'POST',
        url: '/admin/user',
        // The key needs to match your method's input parameter (case-sensitive).
        data: $(this).serialize(),
        //contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){alert(data);},
        error: function(errMsg) {
            alert("errr");
        }
    });
})

$('#DeleteModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('whatever') // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    $.ajax({
        method: 'GET',
        url: '/admin/user/'+id,
        async: false,
        // The key needs to match your method's input parameter (case-sensitive).
        data: {},
        //contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data) {
            $(this )
            var role = '';
            data["roles"].forEach(function(element, key){
                role = role + '<option>'+ element +'</option>';
            });
            modal.find('.modal-body #DelId').val(data["id"])
            modal.find('.modal-body #DelFirstName').val(data["firstName"])
            modal.find('.modal-body #DelLastName').val(data["lastName"])
            modal.find('.modal-body #DelAge').val(data["age"])
            modal.find('.modal-body #DelEmail').val(data["email"])
            modal.find('.modal-body #DelRole').html(role)
        },
        error: function(errMsg) {
            alert("errr");
        }
    });
})

$('#formDelUser').on('submit', function (e) {
    e.preventDefault();
    var id = $('#formDelUser #DelId').val()
    $.ajax({
        type: 'DELETE',
        url: '/admin/user/'+id,
        // The key needs to match your method's input parameter (case-sensitive).
        data: {},
        //contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data){alert(data);},
        error: function(errMsg) {
            alert("errr");
        }
    });
})




$('#street').on('click', function() {
    // действия, которые будут выполнены при наступлении события...
    $('#switch').html("");
    $('#content3').html(""); 
});

$('#house').on('click', function() {
    // действия, которые будут выполнены при наступлении события...
    $('#switch').html("");
    $('#content3').html(""); 
});

$('#switch').on('click', function() {
    // действия, которые будут выполнены при наступлении события...   
    $('#content3').html(""); 
});


$('#content').on('click', '#get-switch', function(e){
    e.preventDefault();
    var street =$('#street').val();
    var house =$('#house').val();
    $.ajax({
        url: '/ipoe/api/api.php?action=getSwitch',         /* Куда пойдет запрос */
        method: 'GET',             /* Метод передачи (post или get) */
        dataType: 'html',          /* Тип данных в ответе (xml, json, script, html). */
        data: {street: street, house: house},     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */            
            $('#switch').html(data);           /* В переменной data содержится ответ от index.php. */
        }
    }); 
})

$('#content2').on('click', '#get-ports', function(e){
    e.preventDefault();
    var house =$('#switch').val();
    $.ajax({
        url: '/ipoe/api/api.php?action=getPorts',         /* Куда пойдет запрос */
        method: 'GET',             /* Метод передачи (post или get) */
        dataType: 'html',          /* Тип данных в ответе (xml, json, script, html). */
        data: {idhouse: house},     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */                   
            $('#content3').html(data);           /* В переменной data содержится ответ от index.php. */
        }
    });     
})

// Создание vlan
$('#content3').on("click",'.add', function(event){
    var number = $(this).parent().prev().html();
    if (number == '0' || number<500000) {
        alert("Некорректный Лицевой счёт!");
        return;        
    }


    var res='';
    $.ajax({
        async: false,
        url: '/ipoe/api/api.php',         /* Куда пойдет запрос */
        method: 'POST',             /* Метод передачи (post или get) */
        dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
        data: {number: number, id: event.target.id, action:"addVlan"},     /* Параметры передаваемые в запросе. */
        success: function(data){   /* функция которая будет выполнена после успешного запроса.  */                   
            //alert(data);           /* В переменной data содержится ответ от index.php. */ 
            if (data["status"] != "ok") {
                alert(data["text"]);
                res = "error";
            }                   
        },
        error: function(data){
            alert("Vlan не создан!");
        }
     });

     if (res == "error") {return};

     $(this).parent().parent().attr("class","");
     $(this).attr("class","del btn btn-outline-danger");
     $(this).html("Del");
 


});

// Удаление vlan
$('#content3').on("click",'.del', function(event){
    var number = $(this).parent().prev().html();
    if (number == '0') {
        alert("Не указан Лицевой счёт!");
        return;        
    }

    var question = prompt('Удалить vlan?', '');

    if (question == 'Yes') {

        var res='';       
        $.ajax({
            async: false,
            url: '/ipoe/api/api.php',         /* Куда пойдет запрос */
            method: 'POST',             /* Метод передачи (post или get) */
            dataType: 'json',          /* Тип данных в ответе (xml, json, script, html). */
            data: {id: event.target.id, action:"delVlan"},     /* Параметры передаваемые в запросе. */
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */                   
                //alert(data);           /* В переменной data содержится ответ от index.php. */
                if (data["status"] != "ok") {
                    alert(data["text"]);
                    res = "error";
                } 

            },
            error: function(data){
                alert("Vlan не удален!");
            }
        });

        if (res == "error") {return};  

         $(this).parent().parent().attr("class","table-success");
         $(this).parent().prev().html(0);
         $(this).attr("class","add btn btn-outline-success");
         $(this).html("Add");
    }
    
});

// Ввод нового Л/с
$('#content3').on("click",'.edit', function(event){
    var value = prompt('Введите лицевой счет', '');
    if ($.isNumeric(value) && value != '0' && value != null && value !='') {
        $(this).html(value); 
        $(this).parent().attr("class","table-warning");  
    }; 
   
});



