$(document).ready(getusers());

//Получение пользователей
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
                    .append('<td><button type="button" class="btn btn-info" data-toggle="modal" data-target="#EditModal" data-whatever="'+ element["id"] +'">Edit</button></td>')
                    .append('<td><button type="button" class="btn btn-danger" data-toggle="modal" data-target="#DeleteModal" data-whatever="'+ element["id"] +'">Delete</button></td>');
                $('#table').append(tr)
            })
        }
    });
}

//Обновление списка пользователей
function updateTable() {
    $('#table').html('')
    getusers();
}

//Создание пользователя
$('#formNewUser').on('submit', function (e) {
    e.preventDefault();
    $.ajax({
        method: 'POST',
        url: '/admin/user',
        data: $(this).serialize(),
        dataType: "json",
        success: function(data){
            updateTable()
            $('#nav-new-user').removeClass("show active")
            $('#nav-new-user-tab').removeClass("active")
            $('#nav-user-table').addClass("show active")
            $('#nav-user-table-tab').addClass("active")
        },
        error: function(errMsg) {
            alert("Error create User!");
        }
    });

})

//Модальное окно удаления пользователя
$('#DeleteModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var id = button.data('whatever')
    var modal = $(this)
    $.ajax({
        method: 'GET',
        url: '/admin/user/'+id,
        async: false,
        data: {},
        dataType: "json",
        success: function(data) {
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

//Модальное окно редактирования пользователя
$('#EditModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget)
    var id = button.data('whatever')
    var modal = $(this)
    $.ajax({
        method: 'GET',
        url: '/admin/user/'+id,
        async: false,
        data: {},
        dataType: "json",
        success: function(data) {
            var role = '';
            modal.find('#EditRole option').removeAttr('selected')
            data["roles"].forEach(function(element, key){
                $('#' + element).attr('selected', 'selected');
            });
            modal.find('.modal-body #EditId').val(data["id"])
            modal.find('.modal-body #EditFirstName').val(data["firstName"])
            modal.find('.modal-body #EditLastName').val(data["lastName"])
            modal.find('.modal-body #EditAge').val(data["age"])
            modal.find('.modal-body #EditEmail').val(data["email"])
            modal.find('.modal-body #EditPassword').val('')
            //modal.find('.modal-body #EditRole').html(role)
        },
        error: function(errMsg) {
            alert("errr");
        }
    });
})

//Удаление пользователя
$('#formDelUser').on('submit', function (e) {
    e.preventDefault();
    var id = $('#formDelUser #DelId').val()
    $.ajax({
        method: 'DELETE',
        url: '/admin/user/'+id,
        data: $(this).serialize(),
        dataType: "json",
        success: function(data){
            $('#DeleteModal').modal('hide')
            updateTable()
        },
        error: function(errMsg) {
            alert("Error Delete!");
        }
    });
})

//Редактирование пользователя
$('#formEditUser').on('submit', function (e) {
    e.preventDefault();
    var id = $('#formEditUser #EditId').val()
    $.ajax({
        method: 'PUT',
        url: '/admin/user/'+id,
        data: $(this).serialize(),
        dataType: "json",
        success: function(data){
            $('#EditModal').modal('hide')
            updateTable()
        },
        error: function(errMsg) {
            alert("Error Edit!");
        }
    });
})




