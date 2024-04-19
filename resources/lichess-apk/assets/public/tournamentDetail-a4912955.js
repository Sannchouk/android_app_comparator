import{m as t,q as e,z as a,C as n,b6 as s,af as o,b9 as i,w as r,o as l,cP as c,cQ as u,cR as m,ag as d,r as h,a7 as p,a5 as f,a8 as g,g as b,av as v,a2 as N,au as w,b as k,ar as y,h as P,s as M,c as C,A as _,ce as B,H as I,am as T,cS as j,cT as O}from"./main.js";import{l as S}from"./layout-e08f9316.js";import{C as F}from"./CountdownTimer-b4d0f21b.js";import{M as x}from"./miniBoard-ec7ad706.js";import{p as D,j as L,w as R,l as A,r as W,t as $}from"./tournamentXhr-acdac63d.js";import{S as q}from"./index-c282ab51.js";import{c as V,C as J}from"./chat-e47e82a7.js";var H={controller(t){let e=!1;function a(t){"backbutton"!==t&&e&&r.backbutton.stack.pop(),e=!1}return{open:function(){r.backbutton.stack.push(a),e=!0},close:a,isOpen:()=>e,root:t}},view:function(r){if(!r.isOpen())return null;return r.root.tournament?t("div",{className:"modal",id:"tournamentFaqModal",oncreate:i},t("header",null,t("button",{className:"modal_close",oncreate:n(s(r.close,"tournamentFaqModal"))},o),t("h2",null,e("tournamentFAQ"))),t("div",{className:"modal_content"},t("div",{className:"tournamentFaq"},t("p",null,e("willBeNotified")),t("h2",null,e("isItRated")),t("p",null,e("someRated")),t("h2",null,e("howAreScoresCalculated")),t("p",null,e("howAreScoresCalculatedAnswer")),t("h2",null,e("berserk")),t("p",null,e("berserkAnswer")),t("h2",null,e("howIsTheWinnerDecided")),t("p",null,e("howIsTheWinnerDecidedAnswer")),t("h2",null,e("howDoesPairingWork")),t("p",null,e("howDoesPairingWorkAnswer")),t("h2",null,e("howDoesItEnd")),t("p",null,e("howDoesItEndAnswer")),t("h2",null,e("otherRules")),t("p",null,e("thereIsACountdown")),t("p",null,a("drawingWithinNbMoves",10))))):null}},Q={controller(t){let e=!1;const a=d(null);function n(t){"backbutton"!==t&&e&&r.backbutton.stack.pop(),e=!1}return{open:function(s){D(t.tournament.id,s).then((t=>{a(t),r.backbutton.stack.push(u(n,"tournamentPlayerInfoModal")),e=!0,h()})).catch(p)},close:n,isOpen:()=>e,root:t,playerData:a}},view:function(a){if(!a.isOpen())return null;if(!a.root.tournament)return null;const s=a.playerData();if(!s)return null;const i=s.player,d=s.pairings,h=d.length?(d.reduce(((t,e)=>t+e.op.rating),0)/d.length).toFixed(0):"0";let p=0;const f=[...d].reverse().map((t=>{const e=t.score;if(null!==e){const t=2===e?p<2:e>2,a=p>1&&e>1?"double":t?"streak":"score";return t?p++:p=0,a}return e})).reverse();return t("div",{className:"modal tournamentInfoModal",id:"tournamentPlayerInfoModal",oncreate:m},t("header",null,t("button",{className:"modal_close",oncreate:n(u(a.close,"tournamentPlayerInfoModal"))},o),t("h2",{className:"tournamentModalHeader"},i.rank+". ",nt(i),i.name+" ("+i.rating+") ")),t("div",{className:"modal_content"},t("div",{className:"tournamentPlayerInfo"},t("table",{className:"tournamentModalStats"},t("tr",null,t("td",{className:"statName"},e("gamesPlayed")),t("td",{className:"statData"},i.nb.game)),t("tr",null,t("td",{className:"statName"},e("winRate")),t("td",{className:"statData"},i.nb.game?(i.nb.win/i.nb.game*100).toFixed(0)+"%":"0%")),t("tr",null,t("td",{className:"statName"},e("berserkRate")),t("td",{className:"statData"},i.nb.game?(i.nb.berserk/i.nb.game*100).toFixed(0)+"%":"0%")),t("tr",null,t("td",{className:"statName"},"Average Opponent"),t("td",{className:"statData"},h)),t("tr",{className:i.performance?"":"invisible"},t("td",{className:"statName"},e("performance")),t("td",{className:"statData"},i.performance)))),t("div",{className:"tournamentPlayerGames"},t("table",{className:"tournamentModalTable",oncreate:l((t=>{const e=c(t);if(e){const t=e.dataset.id,a=e.dataset.color;r.set(`/game/${t}?color=${a}&goingBack=1`)}}),void 0,c)},d.map((function(e,a){let n,s="oppOutcome";return n=void 0===e.score||null===e.score?"*":e.score,null!==f[a]&&(s+=" "+f[a]),t("tr",{className:"list_item","data-id":e.id,"data-color":e.color,key:e.id},t("td",{className:"oppRank"}," ",d.length-a," "),t("td",{className:"oppName"}," ",e.op.name," "),t("td",{className:"oppRating"}," ",e.op.rating," "),t("td",{className:"oppColor"}," ",t("span",{className:"color-icon "+e.color}," ")," "),t("td",{className:s}," ",n," "))}))))))}},U={controller(t){let e=!1;const a=d(null);function n(t){"backbutton"!==t&&e&&r.backbutton.stack.pop(),e=!1}return{open:function(t){r.backbutton.stack.push(u(n,"tournamentTeamInfoModal")),a(t),e=!0,h()},close:n,isOpen:()=>e,root:t,teamId:a}},view:function(e){if(!e.isOpen())return null;const a=e.root.tournament,s=e.teamId();if(!(a&&a.teamBattle&&a.teamStanding&&s))return null;const i=a.teamBattle.teams[s],r=a.teamStanding.find((t=>t.id===s));if(!i||!r)return null;return t("div.modal.tournamentInfoModal",{id:"tournamentTeamInfoModal",oncreate:m},[t("header",[t("buton.modal_close",{oncreate:n(u(e.close,"tournamentTeamInfoModal"))},[o]),t("h2.tournamentModalHeader",[t("span",[r.rank+". "]),t("span.ttc-"+e.root.teamColorMap[s],[i+" "]),t("span",["("+r.score+")"])])]),t("div.modal_content",[t("div.tournamentTeamPlayers",[t("table.tournamentModalTable",[r.players.map((function(e,a){return t("tr.list_item.bglight",{key:e.user.id},[t("td.teamPlayerRank",a+1),t("td.teamPlayerName",e.user.name),t("td.teamPlayerScore",e.score)])}))])])])])}};let E,G=!1;var X={open:function(t){r.backbutton.stack.push(Y),G=!0,E=t},close:Y,view:()=>f("tournament_addtl_info_popup",void 0,(()=>function(){const a=E.tournament,n=a.teamBattle,s=n?n.joinWith.map((t=>[n.teams[t],t])).filter((t=>t[0])):[];return t("form",{id:"tournamentPasswordForm",class:"tournamentForm",onsubmit:function(t){return t.preventDefault(),function(t){const e=t[0].elements,a=e[0].value,n=e[1].value;E.join(a,n),Y()}(t.target)}},t("fieldset",null,t("div",{className:"select_input no_arrow_after"+(a.private?"":" notVisible")},t("div",{className:"text_input_container"},t("label",null,"Password: "),t("input",{type:"text",id:"tournamentPassword",className:"passwordField"}))),t("div",{className:"select_input no_arrow_after"+(a.teamBattle?"":" notVisible")},g.renderSelect("Team","team",s,b.tournament.join.lastTeam,!1))),t("div",{className:"popupActionWrapper"},t("button",{className:"popupAction",type:"submit"},t("span",{className:"fa fa-check"}),e("join"))))}()),G,Y)};function Y(t){"backbutton"!==t&&G&&r.backbutton.stack.pop(),G=!1}function z(a){const s=a.tournament;if(!s)return null;const o="https://lichess.org/tournament/"+s.id;return t("div",{className:"actions_bar"},t("button",{key:"faqButton",className:"action_bar_button fa fa-question-circle",oncreate:n(a.faqCtrl.open,(()=>N.show({text:e("tournamentFAQ"),duration:"short",position:"bottom"})))}),t("button",{key:"shareButton",className:"action_bar_button fa fa-share-alt",oncreate:n((()=>q.share({url:o})),(()=>N.show({text:e("shareGameUrl"),duration:"short",position:"bottom"})))}),a.chat?t("button",{key:"chatButton",className:"action_bar_button fa fa-comments withChip",oncreate:n(a.chat.open,(()=>N.show({text:e("chatRoom"),duration:"short",position:"bottom"})))},a.chat.nbUnread>0?t("span",{className:"chip"},a.chat.nbUnread<=99?a.chat.nbUnread:99):null):t.fragment({key:"noChat"},[]),a.hasJoined?function(a,s){if(s.isFinished||b.game.supportedVariants.indexOf(s.variant)<0)return t.fragment({key:"noWithdrawButton"},[]);return t("button",{key:"withdrawButton",className:"action_bar_button fa fa-flag",oncreate:n(a.withdraw,(()=>N.show({text:e("withdraw"),duration:"short",position:"bottom"})))})}(a,s):function(a,s){if(!k.isConnected()||s.isFinished||b.game.supportedVariants.indexOf(s.variant)<0||!s.verdicts.accepted||s.teamBattle&&0===s.teamBattle.joinWith.length)return t.fragment({key:"noJoinButton"},[]);const o=(s.private||s.teamBattle)&&(i=s,null==i.me)?()=>X.open(a):()=>a.join();var i;return t("button",{key:"joinButton",className:"action_bar_button fa fa-play",oncreate:n(o,(()=>N.show({text:e("join"),duration:"short",position:"bottom"})))})}(a,s))}function K(e,a){return void 0===e?null:[a?a+" ":null,t(F,{seconds:e})]}function Z(a,n){return t("div",{className:"tournamentHeader"},function(e){const a=e.perf.name,n=w(e.clock);return t("div",{className:"tournamentTimeInfo"},t("strong",{className:"tournamentInfo withIcon","data-icon":e.perf.icon},a+" • "+n+" • "+v(e.minutes)))}(a),a.spotlight?(i=a.spotlight,t("div",{className:"tournamentSpotlightInfo"},i.description)):null,function(a,n){return t("div",{className:"tournamentCreatorInfo"},"lichess"===a.createdBy?e("lichessTournaments"):e("by",a.createdBy)," • ",n)}(a,n.startsAt),a.position?(o=a.position,t("div",{className:"tournamentPositionInfo"+(o.wikiPath?" withLink":""),oncreate:l((()=>o&&o.wikiPath&&window.open(`https://en.wikipedia.org/wiki/${o.wikiPath}`,"_blank")))},o.eco+" "+o.name)):null,a.verdicts.list.length>0?function(e){const a=["tournamentConditions",k.isConnected()?"":"anonymous",e.accepted?"accepted":"rejected"].join(" ");return t("div",{className:a},t("span",{className:"withIcon","data-icon":"7"}),t("div",{className:"conditions_list"},e.list.map((e=>t("p",{className:"condition "+("ok"===e.verdict?"accepted":"rejected")},e.condition)))))}(a.verdicts):null,(s=a).isFinished||!s.teamBattle||s.teamBattle.joinWith.length>0?null:t("div",{className:"tournamentNoTeam"},t("span",{className:"withIcon","data-icon":"7"}),t("p",null,"You must join one of these teams to participate!")));var s,o,i}function tt(t){const e=t.target;return e.classList.contains("list_item")?e:y(e,".list_item")}function et(e){const a=e.tournament,s=e.currentPageResults,o=e.page,i=s.length>0?s[0].rank:0,r=s.length>0?s[s.length-1].rank:0,l=o>1,c=o<a.nbPlayers/10,u=k.get(),m=u?u.username:"",d=a.teamBattle;return t("div",{className:"tournamentLeaderboard"},t("ul",{className:"tournamentStandings box"+(e.isLoadingPage?" loading":""),oncreate:n((t=>function(t,e){var a;const n=null===(a=tt(e).dataset.player)||void 0===a?void 0:a.toLowerCase();n&&t.playerInfoCtrl.open(n)}(e,t)),void 0,void 0,tt)},s.map(((a,n)=>function(e,a,n,s,o){const i=n%2==0?"even":"odd",r=a.name===e,l=null!=s?s:0;return t("li",{key:a.name,"data-player":a.name,className:`list_item tournament-list-item ${i}`+(r?" tournament-me":"")},t("div",{className:"tournamentIdentity"},t("span",{className:"flagRank","data-icon":!0===a.withdraw?"b":""}," ",!0===a.withdraw?"":`${a.rank}.`,"   "),t("span",{className:"playerName"},nt(a),`${a.name} (${a.rating})`),t("span",{className:`playerTeam ttc-${l}`}," ",null!=o?o:""," ")),t("div",{className:"tournamentPoints "+(a.sheet.fire?"on-fire":"off-fire"),"data-icon":"Q"},a.score))}(m,a,n,a.team?e.teamColorMap[a.team]:0,a.team&&d?d.teams[a.team]:"")))),t("div",{className:"navigationButtons"+(s.length<1?" invisible":"")},at("W",!e.isLoadingPage&&l,e.first),at("Y",!e.isLoadingPage&&l,e.prev),t("span",{class:"pageInfo"}," ",i+"-"+r+" / "+a.nbPlayers," "),at("X",!e.isLoadingPage&&c,e.next),at("V",!e.isLoadingPage&&c,e.last),a.me?t("button",{className:"navigationButton tournament-me"+(e.focusOnMe?" activated":""),"data-icon":"7",oncreate:n(e.toggleFocusOnMe)},t("span",null,"Me")):null))}function at(e,a,s){return t("button.navigationButton",{"data-icon":e,oncreate:n(s),disabled:!a})}function nt(e){return null==e.title?null:t("span.userTitle",[e.title,t.trust("&nbsp;")])}function st(e){const a=e.tournament,n=a.featured;return n?t("div",{className:"tournamentGames"},t("div",{className:"tournamentMiniBoard"},t(x,{fixed:!1,fen:n.fen,lastMove:n.lastMove,orientation:n.orientation,link:()=>r.set(`/tournament/${a.id}/game/${n.id}?color=${n.orientation}&goingBack=1`),gameObj:n}))):null}function ot(a){if(!a)return null;const s=a.rank;return t("div",{className:"place"+s},t("div",{className:"trophy"}," "),t("div",{className:"username",oncreate:n((()=>r.set("/@/"+a.name)))},nt(a),a.name),t("div",{className:"rating"}," ",a.rating," "),t("table",{className:"stats"},t("tr",null,t("td",{className:"statName"},e("performance")),t("td",{className:"statData"},a.performance)),t("tr",null,t("td",{className:"statName"},e("gamesPlayed")),t("td",{className:"statData"},a.nb.game)),t("tr",null,t("td",{className:"statName"},e("winRate")),t("td",{className:"statData"},(a.nb.win/a.nb.game*100).toFixed(0)+"%")),t("tr",null,t("td",{className:"statName"},e("berserkRate")),t("td",{className:"statData"},(a.nb.berserk/a.nb.game*100).toFixed(0)+"%"))))}function it(e){const a=e.tournament,s=a.teamBattle,o=a.teamStanding;return s&&o?t("div",{className:"tournamentTeamLeaderboard"},t("ul",{className:"tournamentTeamStandings box",oncreate:n((t=>function(t,e){const a=rt(e).dataset.team;a&&t.teamInfoCtrl.open(a)}(e,t)),void 0,void 0,rt)},o.map(((a,n)=>function(e,a,n,s){if(!e)return null;const o=a||0,i=s%2==0?"even":"odd";return t("li",{key:n.id,"data-team":n.id,className:`list_item tournament-list-item ${i}`},t("div",{className:"tournamentIdentity"},t("span",null," ",n.rank+".","   "),t("span",{className:"ttc-"+o}," ",e," ")),t("div",{className:"tournamentPoints"},n.score))}(s.teams[a.id],e.teamColorMap[a.id],a,n))))):null}function rt(t){const e=t.target;return e.classList.contains("list_item")?e:y(e,".list_item")}class lt{constructor(t){var e,a;this.page=1,this.hasJoined=!1,this.focusOnMe=!1,this.isLoadingPage=!1,this.pagesCache={},this.join=P(((t,e)=>{L(this.tournament.id,t,e).then((()=>{this.hasJoined=!0,this.focusOnMe=!0,h()})).catch((t=>{null!=t.body&&"error"in t.body?N.show({text:t.body.error,duration:"short"}):p(t)}))}),1e3),this.withdraw=P((()=>{R(this.tournament.id).then((()=>{this.hasJoined=!1,this.focusOnMe=!1,h()})).catch(p)}),1e3),this.reload=function(t){let e,a;return function(...n){const s=this,o=()=>{a=void 0,e=t.apply(s,n).then((()=>{e=void 0,a&&a()})).catch((()=>{e=void 0,a&&a()}))};e?a=o:o()}}((e=()=>5e3+Math.floor(1e3*Math.random()),a=()=>W(this.id,this.focusOnMe?void 0:this.page).then(this.onReload).catch(p),function(...t){const n=this;return new Promise((s=>{a.apply(n,t).then((()=>setTimeout(s,e()))).catch((()=>setTimeout(s,e())))}))})),this.loadPage=P((t=>{A(this.id,t).then((t=>{this.isLoadingPage=!1,this.page===t.page?this.loadCurrentPage(t):this.setPageCache(t),h()})).catch((t=>{this.isLoadingPage=!1,p(t)}))}),1e3),this.first=()=>{this.page>1&&this.userSetPage(1)},this.prev=()=>{this.page>1&&this.userSetPage(this.page-1)},this.next=()=>{const t=this.tournament.nbPlayers;this.page<t/10&&this.userSetPage(this.page+1)},this.last=()=>{const t=this.tournament.nbPlayers;this.page<t/10&&this.userSetPage(Math.ceil(t/10))},this.toggleFocusOnMe=()=>{this.tournament.me&&(this.focusOnMe=!this.focusOnMe,this.focusOnMe&&this.scrollToMe(),h())},this.myPage=()=>this.tournament.me?Math.floor((this.tournament.me.rank-1)/10)+1:void 0,this.unload=()=>{this.appStateListener.remove()},this.scrollToMe=()=>{const t=this.myPage();void 0!==t&&t!==this.page&&this.setPage(t)},this.onReload=t=>{const e=this.tournament;!t.featured||e&&e.featured&&t.featured.id===e.featured.id||this.socketIface.send("startWatching",t.featured.id),this.tournament=Object.assign(Object.assign(Object.assign({},this.tournament),t),{me:t.me}),this.setPageCache(t.standing),void 0!==this.pagesCache[this.page]&&(this.currentPageResults=this.pagesCache[this.page]),this.hasJoined=!(!t.me||t.me.withdraw),this.focusOnMe&&this.scrollToMe(),t.socketVersion&&M.setVersion(t.socketVersion),this.teamColorMap=t.teamBattle?this.createTeamColorMap(t.teamBattle):{};const a=t.teamBattle;a&&!a.joinWith.includes(b.tournament.join.lastTeam())&&a.joinWith.length>0&&b.tournament.join.lastTeam(a.joinWith[0]),h()},this.id=t.id,this.faqCtrl=H.controller(this),this.playerInfoCtrl=Q.controller(this),this.teamInfoCtrl=U.controller(this),this.tournament=t,this.startsAt=C(new Date(t.startsAt)),this.page=this.tournament.standing.page,this.loadCurrentPage(this.tournament.standing),this.hasJoined=!(!t.me||t.me.withdraw),this.focusOnMe=function(t){return!(!t.me||t.me.withdraw)}(this.tournament),this.scrollToMe();const n=t.featured?t.featured.id:void 0;var s;this.socketIface=M.createTournament(this.id,this.tournament.socketVersion,{reload:(s=this).reload,resync:s.reload,redirect(t){r.set("/tournament/"+s.tournament.id+"/game/"+t,!0)},fen(t){const e=s.tournament.featured;e&&e.id===t.id&&(e.fen=t.fen,e.lastMove=t.lm,h())},message(t){s.chat&&s.chat.append(t)}},n),t.chat&&(this.chat=new J(this.socketIface,t.id,t.chat.lines,void 0,t.chat.writeable,k.isShadowban(),"Tournament")),this.appStateListener=_.addListener("appStateChange",(t=>{t.isActive&&this.reload()})),this.teamColorMap=t.teamBattle?this.createTeamColorMap(t.teamBattle):{},h()}userSetPage(t){this.isLoadingPage||(this.focusOnMe=!1,this.setPage(t))}setPage(t){this.page=t;const e=this.pagesCache[this.page];e?this.currentPageResults=e:this.isLoadingPage=!0,this.loadPage(t),h()}setPageCache(t){this.pagesCache[t.page]=t.players}loadCurrentPage(t){this.setPageCache(t),this.currentPageResults=t.players}createTeamColorMap(t){return Object.keys(t.teams).reduce(((t,e,a)=>Object.assign(Object.assign({},t),{[e]:a})),{})}}var ct={oninit({attrs:t}){$(t.id).then((t=>{this.ctrl=new lt(t)})).catch((t=>{404===t.status?(this.notFound=!0,h()):p(t)}))},oncreate:B,onremove(){M.destroy(),this.ctrl&&this.ctrl.unload()},view(){if(this.notFound)return S.free(I(null,T(e("tournamentNotFound"))),t("div.tournamentNotFound",[t("p",e("tournamentDoesNotExist")),t("p",e("tournamentMayHaveBeenCanceled"))]));if(!this.ctrl)return S.free(j(),null);const a=this.ctrl.tournament,n=I(null,T(t("div.main_header_title.withSub",[t("h1",[t("span.fa.fa-trophy"),this.ctrl.tournament.fullName]),t("h2.header-subTitle.tournament-subtTitle",a.isFinished||a.isStarted?K(a.secondsToFinish,""):K(a.secondsToStart,"Starting in"))]))),s=function(e){const a=e.tournament;return a?t("div.tournamentContainer.native_scroller.page",{className:(a.podium?"finished ":"")+(a.teamBattle?"teamBattle":"")},[Z(a,e),a.podium&&!a.teamBattle?(n=a.podium,t("div",{className:"podium"},ot(n[1]),ot(n[0]),ot(n[2]))):null,a.teamBattle?it(e):null,et(e),a.featured?st(e):null]):null;var n}(this.ctrl),o=z(this.ctrl),i=[...(r=this.ctrl,[H.view(r.faqCtrl),Q.view(r.playerInfoCtrl),U.view(r.teamInfoCtrl),r.chat?V(r.chat):null]),X.view()].filter(O);var r;return S.free(n,s,o,i)}};export default ct;
