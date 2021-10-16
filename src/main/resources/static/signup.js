$(document).on('click', '.sign_btn', function(event){
  var id = $('input[name="id"]').val();
  var password = $('input[name="password"]').val();
  var name = $('input[name="name"]').val();
  
    $.ajax({
      url: '/signup_action',
      data: {
        'id': id,
        'password': password,
        'name': name
      },
      success:function(data) {
        if(data['message'] == 'success'){
            console.log(111);
            alert("데이터가 입력되었습니다")
            location.href="/";
        }
        else if(data['message'] == 'distinct'){
            alert('ID 중복')
        }
        else{
            alert('데이터 입력 실패')
        }
        
    }

})
})

