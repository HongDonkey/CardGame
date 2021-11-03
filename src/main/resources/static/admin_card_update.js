var url = window.location.href;
console.log(url);
url_idx = url.split("=", 2);
console.log(url_idx[1]);
var idx = url_idx[1];
$.ajax({
  url: "/admin_card_update_page",
  data: {
    idx: idx,
  },
  success: function (result) {
    if (result["idx"] && result["name"] && result["atk"] && result["def"]) {
      console.log(result);
      $("#name").val(result["name"]); //input 박스 안에 가져온 값 넣기
      $("#atk").val(result["atk"]);
      $("#def").val(result["def"]);
    }
  },
});

$(document).ready(function () {
  $("#update_btn").click(function () {
    var name = $("#name").val();
    var atk = $("#atk").val();
    var def = $("#def").val();
    var atk_rate = $("#select_atk_rate").val();
    var def_rate = $("#select_def_rate").val();
    var tribe = $("#select_tribe").val();
    $.ajax({
      url: "/card_update_action",
      data: {
        idx : idx,
        name: name,
        atk: atk,
        def: def,
        atk_rate: atk_rate,
        def_rate: def_rate,
        tribe: tribe,
      },
      success: function (result) {
        if (result["message"] == "success") {
          console.log(result);
          alert("데이터가 수정되었습니다");
          location.href = "/card_manage";
        } else {
          console.log(result);
          alert("데이터 입력 실패");
        }
      },
    });
  });
});
