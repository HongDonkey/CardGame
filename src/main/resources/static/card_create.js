$(document).on('click', '.insert_card', function(event){
  var name = $("#name").val()
  var atk = $("#atk").val()
  var def = $("#def").val()
  var atk_rate = $("#select_atk_rate").val()
  var def_rate = $("#select_def_rate").val()
  var tribe = $("#select_tribe").val()
  console.log(name,atk,def,atk_rate,def_rate,tribe);
  
    $.ajax({
      url: '/insert_card',
      data: {
        'name': name,
        'atk': atk,
        'def': def,
        'atk_rate' : atk_rate,
        'def_rate' : def_rate,
        'tribe' : tribe
      },
      success:function(data) {
        if(data["message"] == "success"){
            alert("데이터가 입력되었습니다")
            location.href="/card_manage"
          
        }
        else if(data["message"] == "distinct"){
            alert('이름이 같은 카드가 존재합니다.')

        }
        else{
            alert('데이터 입력 실패')

        }
        
    }

})
})

