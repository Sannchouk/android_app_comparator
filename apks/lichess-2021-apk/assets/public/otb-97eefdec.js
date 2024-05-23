import{a5 as t,m as e,C as o,q as a,a8 as s,g as i,r as n,w as r,az as c,aA as l,ab as p,ag as h,d as u,ay as d,A as m,aB as f,aC as v,ah as b,aD as y,K as k,aE as g,k as w,a2 as P,s as j,aF as C,G as _,aG as G,aH as N}from"./main.js";import{l as F}from"./layout-c7d0a2f4.js";import{e as M,p as S}from"./fen-acee80a0.js";import"./CountdownTimer-265e230d.js";import"./tournamentXhr-b8f81bd7.js";import{s as x,g as T}from"./offlineGames-d3a3eaac.js";import{g as A}from"./game-2ffc1d0e.js";import"./perfs-2bc54d35.js";import"./countries-a19495bf.js";import{a as E,c as I,v as O}from"./crazyValid-11f86c1c.js";import{G as R}from"./GameTitle-0505073c.js";import{B as L}from"./Board-c82f4179.js";import{p as B,v as D}from"./promotion-a7ec34cc.js";import{S as U}from"./index-6048898c.js";import"./CrazyPocket-cee87a95.js";import"./chat-fb252130.js";import"./replay-4865f30a.js";import{i as $}from"./chess-3e18520f.js";import{r as z,a as H,g as W,s as V,R as q,d as K,b as X,c as J,e as Q,f as Y,h as Z}from"./view-e9f38157.js";import{c as tt,a as et}from"./clockSet-43d16603.js";import{I as ot}from"./ImporterCtrl-db688a9e.js";var at={controller(t){let e=!1;function o(t){"backbutton"!==t&&e&&r.backbutton.stack.pop(),e=!1}return{open:function(){r.backbutton.stack.push(o),e=!0},close:o,isOpen:function(){return e},root:t}},view:function(r){return r.isOpen()?t("offline_actions",void 0,(function(){return[z(r.root)].concat(H(r.root),function(t){return[e("div",[e("button[data-icon=A]",{oncreate:o(t.goToAnalysis)},a("analysis"))]),e("div.action",s.renderCheckbox(a("otbFlipPiecesAndInfoAfterMove"),"flipPieces",i.otb.flipPieces,(e=>W.changeOTBMode(t.chessground,e)))),e("div.action",s.renderCheckbox(a("otbUseSymmetricPieces"),"useSymmetric",i.otb.useSymmetric,n))]}(r.root))}),r.isOpen(),r.close):null}},st={controller(t){const e=h(!1);function o(t){"backbutton"!==t&&!0===e()&&r.backbutton.stack.pop(),e(!1)}return{open:function(){r.backbutton.stack.push(o),e(!0)},close:o,isOpen:e,root:t}},view:function(n){return n.isOpen()?t("new_offline_game",void 0,(function(){const t=i.otb.availableVariants,h=n.root.vm.setupFen?t.filter((t=>!c.has(t[1]))):t,u=i.otb.variant(),d=n.root.vm.setupFen&&c.has(u);return e("div",null,e("div",{className:"action"},d?e("div",{className:"select_input disabled"},e("label",{for:"variant"},a("variant")),e("select",{disabled:!0,id:"variant"},e("option",{value:u,selected:!0},l(u).name))):e("div",{className:"select_input"},s.renderSelect("variant","variant",h,i.otb.variant)),n.root.vm.setupFen?e("div",{className:"from_position_wrapper"},e("p",null,a("fromPosition")),e("div",{className:"from_position"},e("div",{style:{width:"130px",height:"130px"},oncreate:o((()=>{n.root.vm.setupFen&&r.set(`/editor/${encodeURIComponent(n.root.vm.setupFen)}`)}))},e(p,{fen:n.root.vm.setupFen,orientation:"white"})))):null,e("div",{className:"select_input"},s.renderSelect(a("clock"),"clock",i.otb.availableClocks,i.otb.clockType,!1,it)),tt(i.otb.clockType(),it)),e("div",{className:"popupActionWrapper"},e("button",{className:"defaultButton",oncreate:o((()=>{n.close(),n.root.startNewGame(i.otb.variant(),n.root.vm.setupFen,i.otb.clockType())}))},a("play"))))}),n.isOpen(),(()=>{n.root.vm.setupFen&&r.set("/otb"),n.close()})):null}};function it(){n()}var nt={controller(t){let e=!1;function o(t){"backbutton"!==t&&e&&r.backbutton.stack.pop(),e=!1}return{open:function(){r.backbutton.stack.push(o),e=!0},close:o,isOpen:function(){return e},root:t,importer:ot()}},view:s=>t("OtbImportGamePopup",void 0,(()=>{const t=i.otb.whitePlayer,n=i.otb.blackPlayer;return e("div",[e("p","Import current game state with following player names on lichess?"),e("form",[e("div.exchange",{oncreate:o((()=>{const e=t(),o=n();t(o),n(e)}))},e("span.fa.fa-exchange.fa-rotate-90")),e("div.importMeta.text_input_container",[e("label",a("white")),e("input[type=text]",{value:t(),oninput:u((e=>{const o=e.target.value.trim();t(o)}),300),onfocus(){this.select()},spellcheck:!1})]),e("div.importMeta.text_input_container",[e("label",a("black")),e("input[type=text]",{value:n(),oninput:u((t=>{const e=t.target.value.trim();n(e)}),300),onfocus(){this.select()},spellcheck:!1})])]),e("div.popupActionWrapper",[s.importer.importing()?e("div",[e("span.fa.fa-hourglass-half"),e("span","Importing...")]):e("button.popupAction.withIcon[data-icon=E]",{oncreate:o((()=>{const t=i.otb.whitePlayer,e=i.otb.blackPlayer;s.root.replay.pgn(t(),e()).then((t=>{s.importer.importGame(t.pgn)}))}))},"Import on lichess")])])}),s.isOpen(),s.close)};class rt{constructor(t,e,o){this.promoting=null,this.goToAnalysis=()=>{r.set(`/analyse/offline/otb/${this.data.player.color}?ply=${this.replay.ply}&curFen=${this.replay.situation().fen}`)},this.save=()=>{x({data:this.data,situations:this.replay.situations,ply:this.replay.ply})},this.saveClock=()=>{this.clock&&this.clock.isRunning()&&this.clock.startStop(),this.save()},this.isClockEnabled=()=>!!this.clock&&void 0===this.clock.flagged()&&void 0!==this.clock.activeSide(),this.toggleClockPlay=()=>{this.clock&&this.isClockEnabled()&&this.clock.startStop()},this.sharePGN=()=>{this.replay.pgn("White","Black").then((t=>U.share({text:t.pgn})))},this.onPromotion=(t,e,o)=>{this.replay.addMove(t,e,o)},this.userMove=(t,e)=>{B.start(this,t,e,this.onPromotion)||this.replay.addMove(t,e)},this.onMove=(t,e,o)=>{o?"atomic"===this.data.game.variant.key?(E.capture(this.chessground,e),d.explosion()):d.capture():d.move()},this.onUserNewPiece=(t,e)=>{const o=this.replay.situation();I.drop(this.data,t,e,o.drops)?this.replay.addDrop(t,e):this.apply(this.replay.situation())},this.onNewPiece=()=>{d.move()},this.onFlag=t=>{V(this,{id:35,name:"outoftime"},"white"===t?"black":"white"),d.dong(),this.onGameEnd(),this.save()},this.onReplayAdded=t=>{const e="white"===t.player?"black":"white";this.clock&&this.clock.clockHit(e),this.data.game.fen=t.fen,this.apply(t),V(this,t.status),A.finished(this.data)&&this.onGameEnd(),this.save(),n()},this.onThreefoldRepetition=t=>{V(this,t),this.save(),this.onGameEnd()},this.onGameEnd=()=>{this.clock&&this.clock.isRunning()&&this.clock.startStop(),this.chessground.stop(),setTimeout((()=>{this.actions.open(),n()}),500)},this.player=()=>this.replay.situation().player,this.jump=t=>(this.chessground.cancelMove(),t<0||t>=this.replay.situations.length||(this.replay.ply=t,this.apply(this.replay.situation())),!1),this.jumpNext=()=>this.jump(this.replay.ply+1),this.jumpPrev=()=>this.jump(this.replay.ply-1),this.jumpFirst=()=>this.jump(this.firstPly()),this.jumpLast=()=>this.jump(this.lastPly()),this.firstPly=()=>0,this.lastPly=()=>this.replay.situations.length-1,this.replaying=()=>this.replay.ply!==this.lastPly(),this.canDrop=()=>!0,this.setupFen=e,this.actions=at.controller(this),this.importGamePopup=nt.controller(this),this.newGameMenu=st.controller(this),this.vm={flip:!1,setupFen:e,savedFen:t?t.data.game.fen:void 0},this.moveList=!!i.game.moveList(),e?(this.newGameMenu.open(),o&&i.otb.variant(o),n()):t&&0!==t.ply||this.newGameMenu.open();const a=i.otb.variant();if(!e)if(t)try{this.init(t.data,t.situations,t.ply)}catch(t){this.startNewGame(a,void 0,i.otb.clockType())}else this.startNewGame(a,void 0,i.otb.clockType());this.appStateListener=m.addListener("appStateChange",(t=>{t.isActive||this.saveClock()}))}unload(){this.appStateListener.remove(),this.saveClock()}init(t,e,o){this.actions.close(),this.data=t;const a=this.data.game.variant.key,s=this.data.game.initialFen;if(this.replay?this.replay.init(a,s,e,o):this.replay=new q(a,s,e,o,this.onReplayAdded,this.onThreefoldRepetition),t.offlineClock){const e=t.offlineClock.clockType;this.clock=et[e](this.onFlag),this.clock.setState(t.offlineClock)}else this.clock=void 0;this.chessground?W.reload(this.chessground,this.data,this.replay.situation()):this.chessground=W.make(this.data,this.replay.situation(),this.userMove,this.onUserNewPiece,this.onMove,this.onNewPiece),n()}startNewGame(t,e,o){const a={variant:t};e&&(a.fen=e);const s=o&&"none"!==o?et[o](this.onFlag):null;$(a).then((t=>{this.init(K({id:"offline_otb",variant:t.variant,initialFen:t.setup.fen,fen:t.setup.fen,player:t.setup.player,color:this.data&&f(this.data.player.color)||t.setup.player,pref:{centerPiece:!0},clock:s?s.getState():null}),[t.setup],0)})).then((()=>{e&&(this.vm.setupFen=void 0,r.History.replaceState(void 0,"/otb"))}))}apply(t){if(t){this.clock&&this.clock.activeSide()!==t.player&&this.clock.toggleActiveSide();const e=t.uciMoves.length?t.uciMoves[t.uciMoves.length-1]:null;this.chessground.set({fen:t.fen,turnColor:t.player,lastMove:e?v(e):null,dests:t.dests,movableColor:t.player,check:t.check})}}}function ct(t){return e("section",{className:"actions_bar"},e("button",{className:"action_bar_button fa fa-ellipsis-v",oncreate:o(t.actions.open)}),e("button",{className:"action_bar_button fa fa-plus-circle",oncreate:o((()=>{t.saveClock(),t.newGameMenu.open()}),(()=>P.show({text:a("createAGame"),duration:"short",position:"bottom"})))}),e("button",{className:"fa fa-share-alt action_bar_button",oncreate:o(t.sharePGN,(()=>P.show({text:a("sharePgn"),duration:"short",position:"bottom"})))}),t.clock?e("button",{className:"fa action_bar_button "+(t.clock.isRunning()?"fa-pause":"fa-play")+(t.isClockEnabled()?"":" disabled"),oncreate:o(t.toggleClockPlay,(()=>P.show({text:a("chessClock"),duration:"short",position:"bottom"})))}):null,w()?e("button",{className:"fa fa-cloud-upload action_bar_button",oncreate:o(t.importGamePopup.open,(()=>P.show({text:a("Import game on lichess"),duration:"short",position:"bottom"})))}):null,Z(t),Y(t))}var lt={oninit({attrs:t}){j.createDefault(),T().then((e=>{this.round=new rt(e,t.fen,t.variant),window.addEventListener("unload",this.round.saveClock)})),C()},oncreate:_,onremove(){G(),this.round&&(this.round.unload(),window.removeEventListener("unload",this.round.saveClock))},view({attrs:t}){let o,s;const n=i.otb.useSymmetric()?"symmetric":void 0;if(this.round&&this.round.data&&this.round.chessground)s=N(e(R,{data:this.round.data})),o=function(t,o){const s=i.otb.flipPieces(),n=b({otb:!0,mode_flip:s,mode_facing:!s,turn_white:"white"===t.chessground.state.turnColor,turn_black:"black"===t.chessground.state.turnColor}),r=t.chessground.getMaterialDiff(),c=a(t.data.player.color),l=a(t.data.opponent.color),p=X(t),h=y(),u=k(),d=e(L,{variant:t.data.game.variant.key,chessground:t.chessground,wrapperClasses:n,customPieceTheme:o}),m=t.clock;return h?[g(u,h)?J(t):null,Q(t,l,r[t.data.opponent.color],"opponent",s,o,m),d,Q(t,c,r[t.data.player.color],"player",s,o,m),ct(t)]:[d,e("section",{className:"table"},Q(t,l,r[t.data.opponent.color],"opponent",s,o,m),p,ct(t),Q(t,c,r[t.data.player.color],"player",s,o,m))]}(this.round,n);else{const e=t.fen||M,i=e?S(e):"white";s=N(a("overTheBoard")),o=O(e,i,void 0,"standard",void 0,n)}return F.board(s,o,void 0,this.round&&(r=this.round,[at.view(r.actions),st.view(r.newGameMenu),nt.view(r.importGamePopup),D(r)]),void 0,this.round&&this.round.data&&this.round.data.player.color||"white");var r}};export default lt;