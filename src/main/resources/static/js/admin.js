console.log("hii");

document.querySelector("#file_input").addEventListener('change',function(event)
{
    var file=event.target.files[0];
    var reader = new FileReader();
    reader.onload=function() {
        document.getElementById("img_preview").src = reader.result;
    };
    reader.readAsDataURL(file);
});

