import{ce as e,H as t,am as o,q as s,m as i,a8 as a,g as l,k as r,b as n}from"./main.js";import{l as c}from"./layout-e08f9316.js";import"./prefs-9b684fe8.js";import{render as m,prefsCtrl as f}from"./clock-5bbd42e4.js";var p={oncreate:e,view(){const e=t(null,o(s("clock")));return c.free(e,i("ul.native_scroller.page.settings_list.multiChoices",[i("li.list_item",a.renderMultipleChoiceButton(s("settingsClockPosition"),[{label:s("positionLeft"),value:"left"},{label:s("positionRight"),value:"right"}],l.game.clockPosition))].concat(r()&&n.isConnected()?m(f):[])))}};export default p;
