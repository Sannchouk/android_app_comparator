import{aF as t,g as a,j as s,w as r,a7 as o,G as e,aG as n,s as i,m as c}from"./main.js";import"./layout-c7d0a2f4.js";import"./fen-acee80a0.js";import"./CountdownTimer-265e230d.js";import"./tournamentXhr-b8f81bd7.js";import"./game-2ffc1d0e.js";import"./perfs-2bc54d35.js";import"./countries-a19495bf.js";import{b as m,L as p}from"./crazyValid-11f86c1c.js";import"./GameTitle-0505073c.js";import"./Board-c82f4179.js";import"./promotion-a7ec34cc.js";import"./index-6048898c.js";import"./CrazyPocket-cee87a95.js";import"./chat-fb252130.js";import"./replay-4865f30a.js";import"./vibrate-6077ecb6.js";import{O as d}from"./OnlineRound-7cebaa35.js";const j={oninit(e){t(),e.attrs.channel&&a.tv.channel(e.attrs.channel),s(a.tv.channel(),e.attrs.flip).then((t=>{t.tv=a.tv.channel(),this.round=new d(!1,e.attrs.id,t,e.attrs.flip,r.reload)})).catch((t=>{o(t),404===t.status&&"best"!==a.tv.channel()&&(a.tv.channel("best"),r.set("/tv/",!0))}))},oncreate:e,onremove(){n(),i.destroy(),this.round&&this.round.unload()},view(){return this.round?m(this.round):c(p)}};export default j;