function isInteger(a) {
    return a % 1 === 0
}



for (i = 0; i < 10; i+= .5) {
    if (isInteger(i)) {
        console.log(i + " is an integer");
    } else {
        console.log(i + " is not an integer")
    }
}

console.log("Hello world!");
console.log(isInteger(5));