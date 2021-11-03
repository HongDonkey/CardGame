$(function () {
  $.ajax({
    url: "/manage_member",
    data: {},
    success: function (result) {
      if (result && result.length) {
        for (var i = 0; i < result.length; i++) {
          var mb_id = result[i]["id"];
          // console.log(typeof mb_id); 
          var item_string = "<tr>";
          item_string = item_string + "<td>" + result[i]["idx"] + "</td>";
          item_string = item_string + "<td>" + mb_id + "</td>";
          item_string = item_string + "<td>" + result[i]["name"] + "</td>";
          item_string = item_string + "<td>" + result[i]["created"] + "</td>";
          item_string = item_string + "<td>" + result[i]["updated"] + "</td>";
          item_string = item_string + '<td><input type="button" value="수정" id="'
           + mb_id + 'update" onclick="updateClick(\'' + mb_id + '\')"></td>';
          item_string = item_string + '<td><input type="button" value="삭제" id="'
           + mb_id + 'delete" onclick="deleteClick(\'' + mb_id + '\')"></td>';
          item_string = item_string + "</tr>";
          $("tbody").append(item_string);
          // console.log(mb_id);
        }
      }
    },
  });
});

function updateClick(id) {
  $.ajax({
    url: "/admin_member_update_page",
    data: {
      id: id,
    },
    success: function (result) {
      console.log(result);
    },
  });
  location.href = "admin_member_update?id=" + id;
  console.log(id);
}

function deleteClick(id) {
    $.ajax({
      url: "/delete_member",
      data: {
        id: id,
      },
      success: function (result) {
          console.log(result);
        if(result['message']=="success"){
            alert("삭제 완료")
            location.href="/admin"
        }
        else{
            alert("삭제 실패")
        }
      },
    });
    
    // console.log(id);
  }

$(document).ready(function() {
  $("#logout").click(function(){
    $.ajax({
      url:'/logout_api',
      data: {
        
      },
      success: function(){
        location.href="/"
      }
      
    })
  })
})
 

$(document).ready(function() {
  $("#create_card").click(function(){
    location.href="/card_create"
  })
})


$(document).ready(function() {
  $("#card_manage").click(function(){
    location.href="/card_manage"
  })
})


