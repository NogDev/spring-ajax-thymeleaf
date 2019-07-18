//função para captura as meta tags 
$("#linkPromocao").on('change', function() {
	
	var url = $(this).val();  // capturando o valor que esta no input linkPromocao
	
	if(url.length > 7){
		$.ajax({
			method: "POST",
			url:"/meta/info?url=" + url,
			cache: false,
			success: function( data ){
				console.log(data);
			}
			
		});
	}
})