import{ce as e,H as a,am as t,q as s,m as n,g as i,o as l,E as r}from"./main.js";import{l as c}from"./layout-e08f9316.js";var o={oncreate:e,view(){const e=a(null,t(s("pieceSet")));function o(e){const a=r(e),t=a&&a.dataset.key;t&&i.general.theme.piece(t)}return c.free(e,function(){const e=i.general.theme.piece();return[n("div.native_scroller.page.settings_list",{oncreate:l(o,void 0,r)},[n("ul",i.general.theme.availablePieceThemes.map((a=>{const[t,i]=a,l=e===t;return n("li.list_item.piece_theme",{className:t+(l?" selected":""),"data-key":t},[s(i||t),l?n("span.fa.fa-check"):null])})))])]}())}};export default o;
