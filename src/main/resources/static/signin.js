$(document).on('click', '.login_btn', function(event){
  var mb_id = $("#mb_id").val();
  var mb_password = $("#mb_password").val();
    $.ajax({
      url: '/login_api',
      data: {
        'user_id': mb_id,
        'user_password': mb_password
      },
      success: function(result) {
        if(result['idx'] && result['name'] && result['id']){
          console.log("1234"+JSON.stringify(result))
          location.href="my_card";
      } else {
        alert("일치하지 않음")
        console.log("456"+JSON.stringify(result));
      }
    }
  })
})

$(document).on('click', '.sign_up', function(event){
  location.href = "signup"
})
