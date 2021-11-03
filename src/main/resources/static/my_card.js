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

