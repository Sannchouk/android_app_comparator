import{W as e}from"./main.js";class t extends e{constructor(){super({name:"StockfishVariants",platforms:["web"]})}async getMaxMemory(){return Promise.resolve({value:1024})}async start(){return new Promise((e=>{this.worker||(this.worker=new Worker("../stockfish.js"),this.worker.onmessage=e=>{const t=new Event("stockfish");t.output=e.data,window.dispatchEvent(t)}),setTimeout(e,1)})).then((()=>{}))}async cmd({cmd:e}){return new Promise((t=>{this.worker&&this.worker.postMessage(e),setTimeout(t,1)})).then((()=>{}))}async exit(){return new Promise((e=>{this.worker&&(this.worker.terminate(),this.worker=void 0),setTimeout(e,1)})).then((()=>{}))}}export{t as StockfishVariantsWeb};