var util=require('util');

// process.argv.forEach(function (val, index, array) {
//   console.log(index + ': ' + val);
// });

var object;
var key=process.argv[3];
var value=process.argv[4];
try {
    object=JSON.parse(process.argv[2]);
} catch (e) {
    console.log("Parse error.");
    process.exit(code=1);
}


function setDeep(path, object, value) {
    var first=path.shift();
    if (path.length == 0) {
	object[first]=value;
    } else {
	var next=object[first];
	if (next) {
	    setDeep(path, next, value);
	} else {
	    next={};
	    object[first]=next;
	    setDeep(path, next, value);
	}
    }
}


var keys=key.split(/\./);
setDeep(keys, object, value);

console.log(JSON.stringify(object));



