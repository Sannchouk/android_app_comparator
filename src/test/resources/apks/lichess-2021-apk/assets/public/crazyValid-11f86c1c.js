import{a5 as a,m as e,D as n,b as t,aK as o,C as r,w as s,q as i,aI as c,aL as l,B as d,aM as u,ah as p,r as m,k as f,ak as b,z as h,a7 as g,aD as v,s as y,K as k,ab as w,aE as _,g as N,al as T,aN as C,aO as O,aP as D,aQ as A,a2 as B,n as j,aR as M,aS as P,aT as U,aU as S,aV as L}from"./main.js";import{l as R}from"./layout-c7d0a2f4.js";import{e as G}from"./fen-acee80a0.js";import{C as x}from"./CountdownTimer-265e230d.js";import{w as V}from"./tournamentXhr-b8f81bd7.js";import{a as I,r as $,f as z,u as E,b as F,g as H,m as Z,c as W,p as K,d as Q,e as q,i as X,h as Y,t as J,j as aa,k as ea}from"./game-2ffc1d0e.js";import{p as na}from"./perfs-2bc54d35.js";import{c as ta}from"./countries-a19495bf.js";import{G as oa}from"./GameTitle-0505073c.js";import{B as ra}from"./Board-c82f4179.js";import{p as sa}from"./promotion-a7ec34cc.js";import{S as ia}from"./index-6048898c.js";import{g as ca,n as la,C as da}from"./CrazyPocket-cee87a95.js";import{c as ua}from"./chat-fb252130.js";import{r as pa,a as ma}from"./replay-4865f30a.js";var fa={view({attrs:p}){const{player:m,mini:f,isOpen:b,close:h,opponent:g,score:v}=p;return m.user?a("miniUserInfos",void 0,(()=>function(a,p,m,f){const b=p.user;if(!a||!b)return e("div",{className:"miniUser"},n.getVdom());const h=t.getUserId()===b.id,g=m.user,v=o(b),y=t.get(),k=y&&y.id,w=k&&a.crosstable&&a.crosstable.nbGames>0;return e("div",{className:"miniUser"},e("div",{className:"title"},e("div",{className:"username",oncreate:r((()=>s.set(`/@/${b.username}`)))},v),b.profile&&b.profile.country?e("p",{className:"country"},e("img",{className:"flag",src:u("images/flags/"+b.profile.country+".png")}),ta[b.profile.country]):null),b.tosViolation?e("div",{className:"warning"},b.tosViolation?i("thisAccountViolatedTos"):""):null,a.perfs?e("div",{className:"mini_perfs"},Object.keys(a.perfs).map((n=>{const t=a.perfs[n];return e("div",{className:"perf"},e("span",{"data-icon":d(n)}),t.games>0?t.rating+(t.prov?"?":""):"-")}))):null,void 0!==k&&w?e("div",{className:"score_wrapper"},l("yourScore",e("span",{className:"score"},`${a.crosstable.users[k]} - ${a.crosstable.users[b.id]}`))):null,!w&&g&&f&&f.nbGames>0?e("div",{className:"score_wrapper"},"Lifetime score ",e("em",null,"vs")," ",c(m),":",e("br",null),e("span",{className:"score"},f.users[b.id])," - ",e("span",{className:"score"},f.users[g.id])):null,h?null:e("div",{className:"mini_user_actions_wrapper"},e("button",{"data-icon":"1",oncreate:r((()=>{s.set(`/@/${b.id}/tv`)}))},i("watchGames"))))}(f,m,g,v)),b,h):null}};var ba={oninit({attrs:a}){const{ctrl:e,color:n}=a;this.clockOnCreate=({dom:a})=>{e.elements[n]=a,e.updateElement(n,e.millisOf(n))},this.clockOnUpdate=({dom:a})=>{e.elements[n]=a,e.updateElement(n,e.millisOf(n))}},view({attrs:a}){const{ctrl:n,color:t,runningColor:o,isBerserk:r}=a,s=n.millisOf(t),i=p({clock:!0,outoftime:!s,running:o===t,berserk:r});return e("div",{className:i,oncreate:this.clockOnCreate,onupdate:this.clockOnUpdate},ha(s,!1))}};function ha(a,e,n=!1){const t=new Date(a),o=t.getUTCMilliseconds(),r=ga(t.getUTCMinutes()),s=ga(t.getUTCSeconds());if(a>=36e5){return ga(t.getUTCHours())+(n&&o<500?" ":":")+r}if(e){let e=Math.floor(o/100).toString();return!n&&a<1e3&&(e+="<huns>"+Math.floor(o/10)%10+"</huns>"),r+":"+s+"<tenths>."+e+"</tenths>"}return r+":"+s}function ga(a){return(a<10?"0":"")+a}function va(a){a.promoting&&a.reloadGameData(),a.promoting=null}var ya={start:function(a,e,n,t){const o=a.chessground.state.pieces.get(n);return!(!o||"pawn"!==o.role||!("8"===n[1]&&"white"===a.player()||"1"===n[1]&&"black"===a.player()))&&(!(3===a.data.pref.autoQueen||2===a.data.pref.autoQueen&&t)&&(a.promoting={orig:e,dest:n,callback:(e,n,t)=>a.sendMove(e,n,t)},m(),!0))},view:a=>sa.view(a,va)},ka={standard:(a,n,t,o,s,c)=>n(a.data)&&f()?e("button",{className:s,"data-icon":t,oncreate:r(c||(()=>{a.socket.iface.send(s)}))},i(o)):null,bookmark:a=>t.isConnected()?e("button",{oncreate:r(a.toggleBookmark),"data-icon":a.data.bookmarked?"t":"s"},[i("bookmarkThisGame")]):null,toggleZen:a=>a.zenAvailable()?e("button",{className:a.isZen()?"on":"",oncreate:r(a.toggleZenMode)},[e("span.fa.fa-volume-off"),i("zenMode")]):null,shareLink:a=>e("button",{oncreate:r((()=>{const e=a.chessground.state.orientation;ia.share({url:`${I(a.data)}/${e}#${a.vm.ply}`})}))},[i("shareGameUrl")]),sharePGN:a=>e("button",{oncreate:r((function(){ca(a.data.game.id).catch((a=>{throw g(a),a})).then((a=>ia.share({text:a})))}))},i("sharePgn")),submitMove:a=>e("div.negotiationButtonsWrapper",[e("p",i("moveConfirmation")),e("div.negotiationButtons",{className:a.vm.submitFeedback?"loading":""},[e("button.accept",{"data-icon":a.vm.submitFeedback?null:"E",oncreate:r((()=>a.submitMove(!0)))},a.vm.submitFeedback?n.getVdom("monochrome white"):null),e("button.decline",{"data-icon":"L",oncreate:r((()=>a.submitMove(!1)))})])]),resign:a=>$(a.data)&&!a.vm.confirmResign?e("button",{className:"resign","data-icon":"b",oncreate:r((()=>{a.vm.confirmResign=!0}))},i("resign")):null,resignConfirmation:a=>$(a.data)&&a.vm.confirmResign?e("div",{className:"negotiation"},e("div",{className:"binary_choice_wrapper"},e("button",{className:"binary_choice","data-icon":"E",oncreate:r((()=>{a.socket.iface.send("resign")}))},i("resign")),e("button",{className:"binary_choice","data-icon":"L",oncreate:r((()=>{a.vm.confirmResign=!1}))},i("cancel")))):null,forceResign:a=>z(a.data)?e("div.force_resign_zone",[e("div.notice",i("opponentLeftChoices")),e("div.binary_choice_wrapper",[e("button.binary_choice.left",{oncreate:r((()=>{a.socket.iface.send("resign-force")}))},i("forceResignation")),e("button.binary_choice.right",{oncreate:r((()=>{a.socket.iface.send("draw-force")}))},i("forceDraw"))])]):null,threefoldClaimDraw:a=>a.data.game.threefold?e("div.claim_draw_zone",[e("div.notice",i("threefoldRepetition")),e.trust("&nbsp;"),e("button[data-icon=E]",{oncreate:r((()=>{a.socket.iface.send("draw-claim")}))},i("claimADraw"))]):null,offerDraw:a=>a.vm.confirmDraw?null:e("button",{className:"draw-yes",oncreate:r((()=>{a.vm.confirmDraw=!0})),disabled:!a.canOfferDraw()},e("span","½"),i("offerDraw")),drawConfirmation:a=>a.canOfferDraw()&&a.vm.confirmDraw?e("div",{className:"negotiation"},e("div",{className:"binary_choice_wrapper"},e("button",{className:"binary_choice","data-icon":"E",oncreate:r((()=>{a.vm.confirmDraw=!1,a.offerDraw()}))},i("offerDraw")),e("button",{className:"binary_choice","data-icon":"L",oncreate:r((()=>{a.vm.confirmDraw=!1}))},i("cancel")))):null,cancelDrawOffer:a=>a.data.player.offeringDraw?e("div.negotiation",[e("div.notice",i("drawOfferSent"))]):null,answerOpponentDrawOffer:a=>a.data.opponent.offeringDraw?e("div.negotiation",[e("div.notice",i("yourOpponentOffersADraw")),e("div.binary_choice_wrapper",[e("button.binary_choice[data-icon=E]",{oncreate:r((()=>{a.socket.iface.send("draw-yes")}))},i("accept")),e("button.binary_choice[data-icon=L]",{oncreate:r((()=>{a.socket.iface.send("draw-no")}))},i("decline"))])]):null,cancelTakebackProposition:a=>a.data.player.proposingTakeback?e("div.negotiation",[e("div.notice",i("takebackPropositionSent")),e("button[data-icon=L]",{oncreate:r((()=>{a.socket.iface.send("takeback-no")}))},i("cancel"))]):null,answerOpponentTakebackProposition:a=>a.data.opponent.proposingTakeback?e("div.negotiation",[e("div.notice",i("yourOpponentProposesATakeback")),e("div.binary_choice_wrapper",[e("button.binary_choice[data-icon=E]",{oncreate:r((()=>{a.acceptTakeback()}))},i("accept")),e("button.binary_choice[data-icon=L]",{oncreate:r((()=>{a.socket.iface.send("takeback-no")}))},i("decline"))])]):null,analysisBoard(a){const n=a.data;return E(n)||F(n)?e("button",{oncreate:r(a.goToAnalysis)},[e("span[data-icon=A].withIcon"),i("analysis")]):null},notes:a=>a.notes?e("button",{oncreate:r((()=>a.notes&&a.notes.open()))},[e("span.fa.fa-pencil.withIcon"),i("notes")]):null,analysisBoardIconOnly(a){const n=a.data;return E(n)||F(n)?e("button.action_bar_button[data-icon=A]",{oncreate:r(a.goToAnalysis)}):null},newOpponent(a){const n=a.data,t=(H.finished(n)||H.aborted(n))&&("lobby"===n.game.source||"pool"===n.game.source);return!a.data.opponent.ai&&t?e("button[data-icon=r]",{oncreate:r((()=>{a.hideActions(),b.onNewOpponent(a.data)}))},i("newOpponent")):null},rematch(a){const n=a.data,t=!n.game.rematch&&(H.finished(n)||H.aborted(n))&&!n.game.tournamentId&&!n.game.boosted&&(n.opponent.onGame||!n.clock&&n.player.user&&n.opponent.user);return a.data.opponent.offeringRematch?e("div.negotiation",[e("div.notice",i("yourOpponentWantsToPlayANewGameWithYou")),e("div.binary_choice_wrapper",[e("button.binary_choice[data-icon=E]",{oncreate:r((()=>{a.socket.iface.send("rematch-yes")}))},i("joinTheGame")),e("button.binary_choice[data-icon=L]",{oncreate:r((()=>{a.socket.iface.send("rematch-no")}))},i("decline"))])]):a.data.player.offeringRematch?e("div.negotiation",[e("div.notice",i("rematchOfferSent")),e("div.notice",i("waitingForOpponent")),e("button[data-icon=L]",{oncreate:r((()=>{a.socket.iface.send("rematch-no")}))},i("cancelRematchOffer"))]):e("button",{oncreate:r((()=>{a.socket.iface.send("rematch-yes")})),disabled:!t},[e("span.fa.fa-refresh"),i("rematch")])},moretime:a=>Z(a.data)?e("button[data-icon=O]",{oncreate:r(a.socket.moreTime)},h("giveNbSeconds",15)):null,flipBoard(a){const n=p({action_bar_button:!0,highlight:a.vm.flip});return e("button",{className:n,"data-icon":"B",oncreate:r(a.flip)})},first(a){const n=a.vm.ply-1,t=a.vm.ply!==n&&n>=a.firstPly(),o=p({action_bar_button:!0,fa:!0,"fa-fast-backward":!0,disabled:!t});return e("button",{className:o,oncreate:r(a.jumpFirst)})},backward(a){const n=a.vm.ply-1,t=a.vm.ply!==n&&n>=a.firstPly(),o=p({action_bar_button:!0,fa:!0,"fa-backward":!0,disabled:!t});return e("button",{className:o,oncreate:r(a.jumpPrev,void 0,a.jumpPrev)})},forward(a){const n=a.vm.ply+1,t=a.vm.ply!==n&&n<=a.lastPly(),o=p({action_bar_button:!0,fa:!0,"fa-forward":!0,disabled:!t});return e("button",{className:o,oncreate:r(a.jumpNext,void 0,a.jumpNext)})},last(a){const n=a.vm.ply+1,t=a.vm.ply!==n&&n<=a.lastPly(),o=p({action_bar_button:!0,fa:!0,"fa-fast-forward":!0,disabled:!t});return e("button",{className:o,oncreate:r(a.jumpLast)})},returnToTournament:a=>e("button",{oncreate:r((function(){a.hideActions();const e=`/tournament/${a.data.game.tournamentId}`;a.data.tv?s.set(e):s.set(e,!0)}))},e("span",{className:"fa fa-trophy"}),i("backToTournament")),withdrawFromTournament:(a,n)=>e("button",{oncreate:r((function(){a.hideActions(),V(n),s.set(`/tournament/${n}`,!0)}))},e("span",{className:"fa fa-flag"}),"Pause"),goBerserk(a){if(!W(a.data))return null;if(a.vm.goneBerserk[a.data.player.color])return null;return e("button",{className:"berserk",oncreate:r((function(){a.hideActions(),a.goBerserk()}))},e("span",{"data-icon":"`"})," GO BERSERK!",e("br",null),e("small",null,"Half the time, bonus point"))}};function wa(a,e){return(a/Math.pow(10,e)).toFixed(e).substr(2)}function _a(a){const e=new Date(a),n=wa(e.getUTCMinutes(),2),t=wa(e.getSeconds(),2);let o,r="";if(a>=864e5){const a=e.getUTCDate()-1;o=e.getUTCHours(),r+=h("nbDays",a),0!==o&&(r+=" "+h("nbHours",o))}else a>=36e5?(o=e.getUTCHours(),r+=wa(o,2)+":"+n):r+=n+":"+t;return r+(f()?"":"?")}function Na(a,n,t){const o=a.data[n],r="correspondence clock "+p({outoftime:!o,running:t===n,emerg:o<a.data.emerg,offline:!f()});return e("div",{className:r,oncreate:function(e){const t=e.dom;t.textContent=_a(1e3*o),a.els[n]=t},onupdate:function(e){const t=e.dom;t.textContent=_a(1e3*o),a.els[n]=t}})}function Ta(a){const n=v();return R.board(function(a){let n;n=a.goingBack||!a.data.tv&&!a.data.userTV&&a.data.player.spectator?[T([Aa(a),C(a.toggleBookmark,a.data.bookmarked)])]:[O(),Aa(a)];return n.push(D()),e("nav",{key:"roundHeader",className:y.isConnected()?"":"reconnecting"},n)}(a),function(a,n){const t=k(),o=a.chessground.getMaterialDiff(),r=Pa(a,a.data.player,o[a.data.player.color],"player"),s=Pa(a,a.data.opponent,o[a.data.opponent.color],"opponent"),i=K(a.data),c=X(a.data),l=e(ra,{variant:a.data.game.variant.key,chessground:a.chessground},i?[c?null:Ba(a,"opponent",c),c?Ba(a,"player",c):null]:[]);return n?[_(t,n)?pa(a):null,s,l,r,La(a)]:[l,e("section.table",[s,ma(a),La(a),r])]}(a,n),"round",function(a){let n=!a.data.opponent.user||a.data.player.spectator?i("chat"):a.data.opponent.user.username;const t=a.data.watchers;a.data.player.spectator&&t&&t.nb>=2?n=i("spectators")+" "+t.nb:a.data.player.spectator&&(n=i("spectatorRoom"));return[a.chat?ua(a.chat,n):null,a.notes?la(a.notes):null,ya.view(a),Sa(a),ja(a),e(fa,{player:a.data.player,opponent:a.data.opponent,mini:a.vm.miniUser.player.data,score:a.score,isOpen:a.vm.miniUser.player.showing,close:()=>a.closeUserPopup("player")}),e(fa,{player:a.data.opponent,opponent:a.data.player,mini:a.vm.miniUser.opponent.data,score:a.score,isOpen:a.vm.miniUser.opponent.showing,close:()=>a.closeUserPopup("opponent")})]}(a),void 0,void 0,"roundView")}function Ca(a){const n=Object.keys(a.pieces).map((n=>e("div.tomb",Array.from(Array(a.pieces[n]).keys()).map((a=>e("piece",{className:n}))))));return a.score>0&&n.push(e("span","+"+a.score)),n}function Oa(a,n,t,o,r,s){const i=v(),c=k(),l=e("section",{className:"board_wrapper"+(r?" "+r:"")},e(w,{fen:a,lastMove:t,orientation:n,variant:o,customPieceTheme:s})),d=_(c,i)&&N.game.moveList()&&!N.game.zenMode();return i?e.fragment({},[d?e("div.replay_inline"):null,e("section.playTable.opponent"),l,e("section.playTable.player"),e("section.actions_bar")]):e.fragment({},[l,e("section.table")])}const Da={view:()=>R.board(U(),Oa(G,"white"),"roundView")};function Aa(a){const n=a.data,o=a.data.tournament;if(a.vm.offlineWatcher||y.isConnected()){const r=!n.player.spectator&&"correspondence"===n.game.speed;return a.data.tv?e("div.main_header_title.withSub",[e("h1.header-gameTitle",[Ua(a)]),e("h2.header-subTitle",J(a.data))]):a.data.userTV?e("div.main_header_title.withSub",[e("h1.header-gameTitle",[e("span.withIcon[data-icon=1]"),a.data.userTV]),e("h2.header-subTitle",[e(`span.withIcon[data-icon=${d(a.data.game.perf)}]`),J(a.data)].concat(o?[" • ",e("span.fa.fa-trophy"),e(x,{seconds:o.secondsToFinish||0})]:[]))]):e(oa,{data:a.data,kidMode:t.isKidMode(),subTitle:o?"tournament":r?"corres":"date"})}return e("div",{className:"main_header_title reconnecting"},P)}function Ba(a,n,t){const o=a.data.expiration;if(!o)return null;const r=Math.max(0,o.movedAt-Date.now()+o.millisToMove);return e("div.round-expiration",{className:n},e(x,{seconds:Math.round(r/1e3),emergTime:t?8:void 0,textWrap:(a,e)=>h("nbSecondsToPlayTheFirstMove",a,`<strong>${e}</<strong>`),showOnlySecs:!0}))}function ja(a){return a.vm.moveToSubmit||a.vm.dropToSubmit||a.vm.submitFeedback?e("div",{className:"overlay_popup_wrapper submitMovePopup"},e("div",{className:"overlay_popup"},ka.submitMove(a))):null}function Ma(a,n,t,o,s){const i=n.user,l=function(a){var n,t,o;if(a.name||a.username||a.user){const r=a.name||a.username||(null===(n=a.user)||void 0===n?void 0:n.username);return e("span",[(null===(t=a.user)||void 0===t?void 0:t.title)?[e("span.userTitle"+("BOT"===(null===(o=a.user)||void 0===o?void 0:o.title)?".bot":""),a.user.title)," "]:[],r])}return a.ai?M({ai:a.ai}):"Anonymous"}(n),d=i?r(i?()=>a.openUserPopup(o,i.id):j,(()=>function(a,e,n){let t;t=a?`${n}: ${a.online?"connected to lichess":"offline"}; ${e.onGame?"currently on this game":"currently not on this game"}`:n;B.show({text:t,position:"center",duration:"short"})}(i,n,c(n)))):r(j,(()=>{var a;return B.show({text:n.name||n.username||(null===(a=n.user)||void 0===a?void 0:a.username),position:"center",duration:"short"})})),u=function(a,e){return(e===a.data.player.color?a.data.opponent:a.data.player).checks}(a,n.color),p=a.isClockRunning()?a.data.game.player:void 0,m=a.data.tournament&&a.data.tournament.ranks?"#"+a.data.tournament.ranks[n.color]+" ":null,f=a.vm.goneBerserk[n.color];return e("div",{className:"antagonistInfos"+(s?" crazy":"")+(a.isZen()?" zen":""),oncreate:d},e("h2",{className:"antagonistUser"},i&&i.patron?e("span",{className:"patron status "+(n.onGame?"ongame":"offgame"),"data-icon":""}):e("span",{className:"fa fa-circle status "+(n.onGame?"ongame":"offgame")}),m,l,f?e("span",{className:"berserk","data-icon":"`"}):null,s&&"opponent"===o&&i&&i.tosViolation?e("span",{className:"warning","data-icon":"j"}):null),s?null:e("div",{className:"ratingAndMaterial"},"opponent"===o&&i&&i.tosViolation?e("span",{className:"warning","data-icon":"j"}):null,i?e("h3",{className:"rating"},n.rating,n.provisional?"?":"",A(n)):null,void 0!==u?e("div",{className:"checkCount"},"+",u):null,a.vm.showCaptured&&"horde"!==a.data.game.variant.key?Ca(t):null),s&&a.clock?e(ba,{ctrl:a.clock,color:n.color,isBerserk:f,runningColor:p}):s&&a.correspondenceClock?Na(a.correspondenceClock,n.color,a.data.game.player):null)}function Pa(a,n,t,o){const r=a.isClockRunning()?a.data.game.player:void 0,s=a.plyStep(a.vm.ply),i=!!s.crazy,c=p({playTable:!0,crazy:i,clockOnLeft:"left"===a.vm.clockPosition,flip:!a.data.tv&&a.vm.flip});return e("section",{className:c+" "+o},Ma(a,n,t,o,i),s.crazy?e(da,{ctrl:a,crazyData:s.crazy,color:n.color,position:o}):null,!i&&a.clock?e(ba,{ctrl:a.clock,color:n.color,isBerserk:a.vm.goneBerserk[n.color],runningColor:r}):!i&&a.correspondenceClock?Na(a.correspondenceClock,n.color,a.data.game.player):null)}function Ua(a){const n=na.filter((a=>"correspondence"!==a[0])).map((a=>[a[1],a[0]]));n.unshift(["Top rated","best"]),n.push(["Bot","bot"]),n.push(["Computer","computer"]);const t=N.tv.channel(),o=d(t);return e("div.select_input.main_header-selector.round-tvChannelSelector",[e("label",{for:"channel_selector"},[e(`i[data-icon=${o}]`),e("span",e.trust("&nbsp;")),e("span","Lichess TV")]),e("select#channel_selector",{value:t,onchange(e){const n=e.target.value;N.tv.channel(n),a.onFeatured&&a.onFeatured(),setTimeout(m,10)}},n.map((a=>e("option",{key:a[1],value:a[1]},a[0]))))])}function Sa(n){const t=K(n.data)?void 0:()=>function(a){const n=aa(a.data),t=ea(a.data,a.data.game.winner),o=H.toLabel(a.data.game.status.name,a.data.game.turns,a.data.game.winner,a.data.game.variant.key)+(t?". "+i("white"===t.color?"whiteIsVictorious":"blackIsVictorious")+".":"");return(H.aborted(a.data)?[]:[e("strong",n),e("br")]).concat([e("em.resultStatus",o)])}(n);return a("player_controls",n.vm.showingShareActions?void 0:t,(()=>K(n.data)?function(a){return e("div.gameControls",{key:"gameRunningActions"},a.data.player.spectator?[ka.shareLink(a)]:[ka.toggleZen(a),ka.analysisBoard(a),ka.notes(a),ka.moretime(a),ka.standard(a,Q,"L","abortGame","abort")].concat(z(a.data)?[ka.forceResign(a)]:[ka.standard(a,q,"i","proposeATakeback","takeback-yes"),ka.cancelTakebackProposition(a),ka.offerDraw(a),ka.drawConfirmation(a),ka.cancelDrawOffer(a),ka.threefoldClaimDraw(a),ka.resign(a),ka.resignConfirmation(a),ka.goBerserk(a),ka.answerOpponentDrawOffer(a),ka.answerOpponentTakebackProposition(a)]))}(n):function(a){let n;const t=a.data.game.tournamentId,o=e("button",{oncreate:r(a.showShareActions)},[e("span.fa.fa-share"),i("shareAndExport")]);return n=a.vm.showingShareActions?[ka.shareLink(a),ka.sharePGN(a)]:t?a.data.player.spectator?[o,ka.analysisBoard(a),ka.returnToTournament(a)]:[o,ka.analysisBoard(a),ka.withdrawFromTournament(a,t),ka.returnToTournament(a)]:a.data.player.spectator?[o,ka.analysisBoard(a)]:[o,ka.analysisBoard(a),ka.notes(a),ka.newOpponent(a),ka.rematch(a)],e("div.game_controls",{key:"gameEndedActions"},e.fragment({key:a.vm.showingShareActions?"shareMenu":"menu"},n))}(n)),n.vm.showingActions,n.hideActions)}function La(a){const n=(a.data.opponent.proposingTakeback||a.data.opponent.offeringDraw)&&!H.finished(a.data)||z(a.data)||a.data.opponent.offeringRematch,t=(a.data.opponent.proposingTakeback?["fa","fa-mail-reply"]:a.data.opponent.offeringDraw?[]:["fa","fa-list"]).concat(["action_bar_button",n?"glow":""]).join(" ");return e("section",{className:"actions_bar"},e("button",{className:t,oncreate:r(a.showActions)},a.data.opponent.offeringDraw?"½":""),a.chat&&!a.isZen()?e("button",{className:"action_bar_button fa fa-comments withChip",oncreate:r(a.chat.open)},a.chat.nbUnread>0?e("span",{className:"chip"},a.chat.nbUnread<=99?a.chat.nbUnread:99):null):null,Y(a.data)?function(a){return e("button.action_bar_button[data-icon=A].withChip",{oncreate:r(a.goToAnalysis)},(a.data.forecastCount||0)>0?e("span.chip",a.data.forecastCount):null)}(a):null,ka.flipBoard(a),K(a.data)?null:ka.analysisBoardIconOnly(a),ka.backward(a),ka.forward(a))}function Ra(a,e){const n=[],t=new Map,o=S(e);for(let r=-1;r<2;r++)for(let s=-1;s<2;s++){const i=L([o[0]+r,o[1]+s]);if(i){n.push(i);const o=a.state.pieces.get(i);o&&(i===e||"pawn"!==o.role)&&t.set(i,null)}}a.setPieces(t),a.explode(n)}var Ga={capture:Ra,enpassant:function(a,e,n){const t=S(e),o=[t[0],t[1]+("white"===n?-1:1)];Ra(a,L(o))}},xa={drop(a,e,n,t){if(!a.game.offline&&!X(a))return!1;if("pawn"===e&&("1"===n[1]||"8"===n[1]))return!1;if(null==t)return!0;return-1!==(Array.isArray(t)?t:t.match(/.{2}/g)||[]).indexOf(n)}};export{Da as L,Ga as a,Ta as b,xa as c,_a as d,ha as f,ya as p,Ca as r,Oa as v};