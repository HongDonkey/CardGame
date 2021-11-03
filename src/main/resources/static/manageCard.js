$(function () {
  $.ajax({
    url: "/card_manage_api",
    data: {},
    success: function (result) {
      if (result && result.length) {
        for (var i = 0; i < result.length; i++) {
          var card_idx = result[i]["idx"];
          // console.log(typeof mb_id); 
          var item_string = "<tr>";
          item_string = item_string + "<td>" + card_idx + "</td>";
          item_string = item_string + "<td>" + result[i]["name"] + "</td>";
          item_string = item_string + "<td>" + result[i]["atk"] + "</td>";
          item_string = item_string + "<td>" + result[i]["def"] + "</td>";
          item_string = item_string + "<td>" + result[i]["atk_rate"] + "</td>";
          item_string = item_string + "<td>" + result[i]["def_rate"] + "</td>";
          item_string = item_string + "<td>" + result[i]["tribe"] + "</td>";
          item_string = item_string + '<td><input type="button" value="수정" id="'
           + card_idx + 'update" onclick="updateClick(' + card_idx + ')"></td>';
          item_string = item_string + '<td><input type="button" value="삭제" id="'
           + card_idx + 'delete" onclick="deleteClick(' + card_idx + ')"></td>';
          item_string = item_string + "</tr>";
          $("tbody").append(item_string);
          // console.log(mb_id);
        }
      }
    },
  });
});

function updateClick(idx) {
  $.ajax({
    url: "/admin_card_update_page",
    data: {
      idx: idx,
    },
    success: function (result) {
      console.log(result);
    },
  });
  location.href = "admin_card_update?idx=" + idx;
  console.log(idx);
}

function deleteClick(idx) {
    $.ajax({
      url: "/delete_card",
      data: {
        idx: idx,
      },
      success: function (result) {
          console.log(result);
        if(result['message']=="success"){
            alert("삭제 완료")
            location.href="/card_manage"
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
  $("#manage_member").click(function(){
    location.href="/admin"
  })
})
