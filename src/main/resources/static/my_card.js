// var number1 = 0;
// var number2 = 0;
// $(document).on('click', '.player_list button', function(event){
//   console.log("======"+Number($(this).parents('li').find('.no').text()));
//   if (number1 == Number($(this).parents('li').find('.no').text()) || number2 == Number($(this).parents('li').find('.no').text())) {
//     return;
//   }
//   if (number1 > 0 && number2 > 0) {
//     number1 = number2;
//     number2 = Number($(this).parents('li').find('.no').text());
//     $('[name="select1"]').val(number1);
//     $('[name="select2"]').val(number2);
//   } else if (number1 > 0) {
//     number2 = Number($(this).parents('li').find('.no').text());
//     $('[name="select1"]').val(number1);
//     $('[name="select2"]').val(number2);
//   } else {
//     number1 = Number($(this).parents('li').find('.no').text());
//     $('[name="select1"]').val(number1);
//   }
// });
var url = window.location.href
// console.log(url);
url_id = url.split("=",2)
// console.log(url_id[1]);
var id = url_id[1]
$(function () {
  $.ajax({
    url: "/card_manage_api",
    data: {},
    success: function (result) {
      idxLength = result.length
      // console.log(idxLength)
      
    }
  });
});

$(document).on('click', '.logout_button', function(event){
  $.ajax({
    url:'/logout_api',
    data: {
      
    },
    success: function(){
      location.href="/"
    }
    
  })

})

$(document).on('click', '.update_button', function(event){
 
      location.href="update"

	});
  let num = 1;
  arr=[];
$(document).ready(function() {
    $("#makeCard").click(function(){
      
      $.ajax({
        url:'/makeCard',
        data: {         
        },
        success: function(result){
          
            if (result && result.length) {              
              for (var i = 0; i < result.length; i++) {
                // console.log(typeof mb_id); 
                var card_space = "<li>";
                card_space = card_space + "<span class=no>"+num+"</span>"
                card_space = card_space + "<h3 id='name'>" + result[i]["name"] + "</h3>";
                card_space = card_space + "<span id='atk'>공격력 : <span>" + result[i]["atk"] + "<br />";
                card_space = card_space + "<span id='def'>방어력 : <span>" + result[i]["def"] + "<br />";
                card_space = card_space + "<span id='atk_rate'>공격확률 : <span>" + result[i]["atk_rate"] + "<br />";
                card_space = card_space + "<span id='def_rate'>방어확률 : <span>" + result[i]["def_rate"] + "<br />";
                card_space = card_space + "<span id='tribe'>종족 : <span>" + result[i]["tribe"] + "<br />"
                card_space = card_space + "<button id='"+num+"selectButton' onclick='choiceCard(\"" + num + "\")'>카드선택</button>"
                card_space = card_space + "</li>"
                $("#player_list").append(card_space);
                arr.push(result[i]["name"],result[i]["atk"],result[i]["def"],result[i]["atk_rate"],result[i]["def_rate"],result[i]["tribe"])
                // $("tbody").append(item_string);
                // console.log(id);
              }
            }
            
            num++
            console.log(arr);
            var name = arr[0]
            var atk = arr[1]
            var def = arr[2]
            var atk_rate = arr[3]
            var def_rate = arr[4]
            var tribe = arr[5]
            console.log(id, name, atk, def, atk_rate, def_rate, tribe);
            // $.ajax({
            //   url:'/insertMycard',
            //   data: {
            //     id:id,
            //     name:name,
            //     atk:atk,
            //     def:def,
            //     atk_rate:atk_rate,
            //     def_rate:def_rate,
            //     tribe:tribe
            //   },
            //   success: function(data){
            //     if(data["message"] == "success"){
            //       alert("입력완료")
            //       location.href="my_card?id="+id
            //     } else {
            //       alert("데이터 입력 실패")
            //     }                
            //   }
            // })
        }
      })
      //var name = $("#name").val() , .text() 다 안됨

      // console.log(name, atk, def, atk_rate, def_rate, tribe);
      // $.ajax({
      //   url:'/insertMyCard',
      //   data: {
      //     id:id
      //   }
      // })
    })
    
  })

  function choiceCard(num) {
    var name = arr[0]
    // $.ajax({
    //   url: "/admin_member_update_page",
    //   data: {
    //     id: id,
    //   },
    //   success: function (result) {
    //     console.log(result);
    //   },
    // });
    // location.href = "admin_member_update?id=" + id;
    console.log(id, num, name);
  }

 
