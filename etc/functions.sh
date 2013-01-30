

# Convert to/from json and pretty print.
# Currently only for property key/values in az

EXAMPLE='{"a":"hello","b":"goodbye","c":"death"}'

pretty-print() {
    local json=$1
    echo $json | sed -e 's|{"||' -e 's|":"| |g' -e 's|"}||' -e 's|","|\n|g'
}

get-property() {
    local key=$2
    local json=$1
    pretty-print $json | grep "^$key " | sed -e 's|^[^ ]* ||'
}

ugly-print() {
    local result="{"
    local first=1
    while read key value; do
	if [ "$first" -ne 1 ]; then
	    result="$result,"
	fi
	first=0
	result="$result\"$key\":\"$value\""
    done
    echo "${result}}"
}

set-property() {
    local json="$1"
    local key=$2
    local value="$3"
    ( pretty-print $json | grep -v "^$key "; echo "$key $value" ) | ugly-print
}