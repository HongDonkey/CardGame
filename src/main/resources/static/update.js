$.ajax({
  url:'/update_api',
  data: {
    
  },
  success: function(result){
    
  if(result['idx'] && result['name'] && result['id']){
    console.log(result);
    $("#id").val(result['id']) //input 박스 안에 가져온 값 넣기
    $("#name").val(result['name']) 
  }
}
})


$(document).ready(function() {
$('#update_btn').click(function() {
  var id = $("#id").val();
  var password = $("#password").val();
  var name = $("#name").val();
  console.log(2);
  $.ajax({
    url: '/update_action',
    data: {
      'id': id,
      'password': password,
      'name': name
    },
    success:function(result) {
      if(result['message']=='success'){
          console.log(result);
          alert("데이터가 수정되었습니다")
          location.href="/my_card"
          
        
      }
      else{
          console.log(result);
          alert('데이터 입력 실패')
          
      }
      
  }

})

})
})