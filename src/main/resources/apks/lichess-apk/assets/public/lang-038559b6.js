import{ce as a,cH as e,cI as s,H as t,am as n,q as r,m as c,cJ as o,o as l,E as i,cK as u,cL as m,r as f,cM as g}from"./main.js";import{l as d}from"./layout-e08f9316.js";var p={oncreate:a,oninit(){this.browserLangs=navigator.languages.reduce(((a,t)=>{if(void 0!==e[t])return new Set([...a,t]);{const e=s(t);if(e)return new Set([...a,e])}return a}),new Set)},view(){const a=t(null,n(r("language"))),s=g();const p=a=>{const e=i(a),s=e&&e.dataset.locale;s&&(u(s),m(s).then(f))};return d.free(a,(()=>c("ul",{className:"native_scroller page settings_list",oncreate:l(p,void 0,i)},[...this.browserLangs].concat(o).map((a=>function(a){const t=e[a],n=a===s;return c("li",{className:"list_item"+(n?" selected":""),"data-locale":a},t,n?c("span",{className:"fa fa-check"}):null)}(a)))))())}};export default p;