#!/bin/bash

node-convert() {
    local CMD=$1
    local OBJECT=$2
    shift; shift
    local PAR_DEC="["
    for i in "${@}"; do
	PAR_DEC="$PAR_DEC '$i',"
    done
    PAR_DEC="$PAR_DEC ]"
    node <<EOF
var cmd="$CMD"
var strObject='$OBJECT'
var parameters=$PAR_DEC

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

var object;
try {
  object=JSON.parse(strObject);
} catch (e) {  process.exit(code=1);}

parameters.forEach(function (val, index, array) {
   var i=val.indexOf('=');
   var keys=val.substring(0,i).split(/\./);
   var value=val.substring(i+1);
   setDeep(keys, object, value);
 });

if (cmd=='pp') {
  console.log(JSON.stringify(object, null, 4))
} else {
  console.log(JSON.stringify(object));
}

EOF
}


XX=$(node-convert json "{\"a\": \"jon\", \"b\":{ \"c\": \"value\" }}" x=anne b.d.e=Siri a=noj)
echo "///$XX///"

node-convert pp "{\"a\": \"jon\", \"b\":{ \"c\": \"value\" }}"

