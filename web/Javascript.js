function resetDropDownMenu(){
    $("#reset").on("click",function(){
        $("#dropdropselect").prop('selected',function(){
            return this.defaultSelected;
        });
    });
}
function lukVindue() {
    $("#okKnap").close();
}