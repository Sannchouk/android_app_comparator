import{s as t,G as e,H as a,q as r,m as o,a8 as s,g as m}from"./main.js";import{l as i}from"./layout-e08f9316.js";import{I as n}from"./ImporterCtrl-53e54ef4.js";const l={oninit(){t.createDefault(),this.ctrl=n()},oncreate:e,view(){const t=a(r("importGame")),e=(n=this.ctrl,o("div.gameImporter.native_scroller",[o("p",r("importGameExplanation")),o("form",{onsubmit:t=>{t.preventDefault();const e=t.target[0].value;e&&n.importGame(e)}},[o("label",r("pasteThePgnStringHere")+" :"),o("textarea.pgnImport"),s.renderCheckbox(r("requestAComputerAnalysis"),"analyse",m.importer.analyse),o("button.fatButton",n.importing()?o("div.fa.fa-hourglass-half"):r("importGame"))])]));var n;return i.free(t,e)}};export default l;