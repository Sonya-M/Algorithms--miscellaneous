// YES! IT WORKS!!!
// TODO: Check if this is the most efficient js way of doing it

function mergeSort(arr) {
    //TODO: delete after debugging:
    // console.log("Calling mergeSort on array: " + arr);
    //if arr.length === 1, return it - it's already sorted
    // else split the array in half, sort each half, then merge
    if (arr.length <= 1) {
        return arr;
    } else {
    let middle = arr.length / 2;
    let arr1 = arr.slice(0, middle);
    let arr2 = arr.slice(middle);
    // console.log("arr1: " + arr1 + "; arr2: " + arr2);
    return merge(mergeSort(arr1), mergeSort(arr2));  
     //you shouldn't be returning anything here, only in base case
    }
}

function merge(arr1, arr2) {
    let result = [];
    let i = 0, j = 0; // i indexes arr1, j indexes arr2
    while(i < arr1.length && j < arr2.length) {
        if (arr1[i] <= arr2[j]) result.push(arr1[i++]);
        else result.push(arr2[j++]);
    }
    while (i < arr1.length) result.push(arr1[i++]);
    while (j < arr2.length) result.push(arr2[j++]);
    return result;
}

const arraySize = 10000;
let arr = [];
for (let i = 0; i < arraySize; i++){
    arr.push(Math.floor(Math.random() * arraySize+1));
}
console.log("Unsorted array: " + arr);
sorted = mergeSort(arr);
console.log("Sorted array: " + sorted);