//////////////////////////////////////////
// BASIC FUNCTIONS - MY IMPLEMENTATIONS //
//////////////////////////////////////////
function isInteger(a) {
    return a % 1 === 0
}

function round(n) {
    return intDivision(n + 0.5, 1);
}

function intDivision(n, m) {
    // n/m = d + r/m --->>> d = n/m - r/m = (n-r)/m
    return (n - (n % m)) / m;
}

// rounds n to 2 decimal places;
// CAVEAT: NOT THAT PRECISE - rounds 2.4999 to 2.5
function roundTo2DecPlaces(n) {
    n *= 100;
    n = round(n);
    return n/100;
}

// rounds n to dec decimal places
// CAVEAT: NOT THAT PRECISE - for arguments (2.4999, 3) returns 2.5
function roundToNDecPlaces(n, dec) {
    var mul = 1;
    for (var i = 0; i < dec; i++) {
        mul *= 10;
    }
    n *= mul;
    n = round(n);
    return n/mul;
}

////////////////////
// TEST FUNCTIONS //
////////////////////


function testRoundTo2DecPlaces() {
    var n = [3.4568734, 2.4999, 2.12345, 2.00, 2.01, 2.99];
    console.log("Testing roundTo2DecPlaces............")
    for (var i = 0; i < n.length; i++) {
        console.log(n[i] + " >>> " + roundTo2DecPlaces(n[i]));
    }
}

function testRoundToNDecPlaces() {
    var n = [3.4568734, 2.4999, 2.12345, 2.00, 2.01, 2.99];
    var dec = [0, 1, 2, 3, 4, 5];
    console.log("Testing roundToNDecPlaces.............");
    for (var i = 0; i < n.length; i++) {
        for (var j = 0; j < dec.length; j++) {
            console.log(n[i] + " rounded to " + dec[j] 
            + " decimal places >>> " + roundToNDecPlaces(n[i], dec[j]));
        }
    }
}

function testIsInteger() {
    for (i = 0; i < 10; i+= .5) {
        if (isInteger(i)) {
            console.log(i + " is an integer");
        } else {
            console.log(i + " is not an integer")
        }
    }
}

function testIntDivision() {
    var ns = [3, 4, 5, 6, 7];
    var m = 2;
    for (var i = 0; i < ns.length; i++) {
        console.log(ns[i] + " / " + m + " using integer division....");
        console.log(intDivision(ns[i], m));
    }
}

function testRound() {
    var nums = [2.3456, 3.5, 3.55, 3.45, 3, 2.99, 2.01, 2.1, 1, 0];
    for (var i = 0; i < nums.length; i++) {
        console.log("Rounding " + nums[i] + "...");
        console.log(round(nums[i]));
    }
}

// source: https://stackoverflow.com/questions/3177836/how-to-format-time-since-xxx-e-g-4-minutes-ago-similar-to-stack-exchange-site
/**
 * @param {number} timestampInMillis date in UNIX time, in milliseconds
 * @returns {string} interval btw now and timestampInMillis, in human readable form
 */
export function getTimeAgo(timestampInMillis) {
  const seconds = Math.floor((Date.now() - timestampInMillis) / 1000);
  let interval = Math.floor(seconds / 31536000); // 31,536,000 seconds in a year
  if (interval > 1) return `${interval} years`;
  interval = Math.floor(seconds / 2592000); // 2,592,000 seconds in a month/30 days
  if (interval > 1) return `${interval} months`;
  interval = Math.floor(seconds / 86400);
  if (interval > 1) return `${interval} days`;
  interval = Math.floor(seconds / 3600);
  if (interval > 1) return `${interval} hours`;
  interval = Math.floor(seconds / 60);
  if (interval > 1) return `${interval} minutes`;
  return `${Math.floor(seconds)} seconds`;
}

/**
 * @param {string} dateString string in a valid date fromat
 * @returns date string in the format DD Mon YYYY (e.g. "25 Mar 1972")
 */
export function formatDate(dateString) {
  return new Intl.DateTimeFormat('en-GB', { dateStyle: 'medium' })
    .format(Date.parse(dateString));
}

/**
 * @param {string} url 
 * @returns {string} domain of given url
 */
export function getDomain(url) {
  let domain = (url).substring((url).indexOf("/") + 2);
  domain = domain.substring(0, domain.indexOf("/"));
  if (domain.indexOf("www.") === 0) {
    domain = domain.substring(4);
  }
  return domain;
}

// testRoundTo2DecPlaces();
// testRoundToNDecPlaces();
// testIsInteger();
// testIntDivision();
// testRound();