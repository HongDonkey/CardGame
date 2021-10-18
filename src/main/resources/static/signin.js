$(document).on('click', '.login_btn', function(event){
  var id = $('input[name="id"]').val();
  var password = $('input[name="password"]').val();
    $.ajax({
      url: '/login_api',
      data: {
        id: id,
        password: password
      },
      success: function(data) {
        if(data['message'] == 'success'){
          location.href="my_card";
      } else {
        alert("일치하지 않음")
      }
    }
  })
})

$(document).on('click', '.sign_up', function(event){
  location.href = "signup"
})
