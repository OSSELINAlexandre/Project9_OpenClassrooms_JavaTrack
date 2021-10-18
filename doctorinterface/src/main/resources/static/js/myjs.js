let choice = 0;

function getTheId(index){

    console.log("Maybe, maybe not ! :)")
    return "#DeleteModal" + index;
}

function myFunction(index){
    console.log("HEY MY MAN" + index);
    choice = index;
    console.log("HEY MY second man" + choice);
}

function openWindow(){
    window.open
}

function deleteThePatient(){
    console.log("Do we get here ...");
    return choice;
}