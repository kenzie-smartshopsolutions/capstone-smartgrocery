/*
    _   _____ _    __
   / | / /   | |  / /
  /  |/ / /| | | / /
 / /|  / ___ | |/ /
/_/ |_/_/  |_|___/

  Descript:
  js for nav in all pages

  File name:     nav.js
  Maintainer:    Kyle Gortych
  Created:       05-08-2022
  Last Modified: 01/13/2024
*/

var prevScrollpos = window.pageYOffset;
window.onscroll = function() {
var currentScrollPos = window.pageYOffset;
  if (prevScrollpos > currentScrollPos) {
    document.getElementById("nav-menu").style.top = "0";
  } else {
    document.getElementById("nav-menu").style.top = "-150px";
  }
  prevScrollpos = currentScrollPos;
}