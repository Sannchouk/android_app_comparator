import{W as t}from"./main.js";function e(t){const e=t.split("/").filter((t=>"."!==t)),i=[];return e.forEach((t=>{".."===t&&i.length>0&&".."!==i[i.length-1]?i.pop():i.push(t)})),i.join("/")}class i extends t{constructor(){super(...arguments),this.DB_VERSION=1,this.DB_NAME="Disc",this._writeCmds=["add","put","delete"]}async initDb(){if(void 0!==this._db)return this._db;if(!("indexedDB"in window))throw this.unavailable("This browser doesn't support IndexedDB");return new Promise(((t,e)=>{const r=indexedDB.open(this.DB_NAME,this.DB_VERSION);r.onupgradeneeded=i.doUpgrade,r.onsuccess=()=>{this._db=r.result,t(r.result)},r.onerror=()=>e(r.error),r.onblocked=()=>{}}))}static doUpgrade(t){const e=t.target.result;switch(t.oldVersion){case 0:case 1:default:e.objectStoreNames.contains("FileStorage")&&e.deleteObjectStore("FileStorage");e.createObjectStore("FileStorage",{keyPath:"path"}).createIndex("by_folder","folder")}}async dbRequest(t,e){const i=-1!==this._writeCmds.indexOf(t)?"readwrite":"readonly";return this.initDb().then((r=>new Promise(((o,s)=>{const a=r.transaction(["FileStorage"],i).objectStore("FileStorage")[t](...e);a.onsuccess=()=>o(a.result),a.onerror=()=>s(a.error)}))))}async dbIndexRequest(t,e,i){const r=-1!==this._writeCmds.indexOf(e)?"readwrite":"readonly";return this.initDb().then((o=>new Promise(((s,a)=>{const n=o.transaction(["FileStorage"],r).objectStore("FileStorage").index(t)[e](...i);n.onsuccess=()=>s(n.result),n.onerror=()=>a(n.error)}))))}getPath(t,e){const i=void 0!==e?e.replace(/^[/]+|[/]+$/g,""):"";let r="";return void 0!==t&&(r+="/"+t),""!==e&&(r+="/"+i),r}async clear(){(await this.initDb()).transaction(["FileStorage"],"readwrite").objectStore("FileStorage").clear()}async readFile(t){const e=this.getPath(t.directory,t.path),i=await this.dbRequest("get",[e]);if(void 0===i)throw Error("File does not exist.");return{data:i.content?i.content:""}}async writeFile(t){const e=this.getPath(t.directory,t.path);let i=t.data;const r=t.encoding,o=t.recursive,s=await this.dbRequest("get",[e]);if(s&&"directory"===s.type)throw Error("The supplied path is a directory.");const a=e.substr(0,e.lastIndexOf("/"));if(void 0===await this.dbRequest("get",[a])){const e=a.indexOf("/",1);if(-1!==e){const i=a.substr(e);await this.mkdir({path:i,directory:t.directory,recursive:o})}}if(!r&&(i=i.indexOf(",")>=0?i.split(",")[1]:i,!this.isBase64String(i)))throw Error("The supplied data is not valid base64 content.");const n=Date.now(),d={path:e,folder:a,type:"file",size:i.length,ctime:n,mtime:n,content:i};return await this.dbRequest("put",[d]),{uri:d.path}}async appendFile(t){const e=this.getPath(t.directory,t.path);let i=t.data;const r=t.encoding,o=e.substr(0,e.lastIndexOf("/")),s=Date.now();let a=s;const n=await this.dbRequest("get",[e]);if(n&&"directory"===n.type)throw Error("The supplied path is a directory.");if(void 0===await this.dbRequest("get",[o])){const e=o.indexOf("/",1);if(-1!==e){const i=o.substr(e);await this.mkdir({path:i,directory:t.directory,recursive:!0})}}if(!r&&!this.isBase64String(i))throw Error("The supplied data is not valid base64 content.");void 0!==n&&(i=void 0===n.content||r?n.content+i:btoa(atob(n.content)+atob(i)),a=n.ctime);const d={path:e,folder:o,type:"file",size:i.length,ctime:a,mtime:s,content:i};await this.dbRequest("put",[d])}async deleteFile(t){const e=this.getPath(t.directory,t.path);if(void 0===await this.dbRequest("get",[e]))throw Error("File does not exist.");if(0!==(await this.dbIndexRequest("by_folder","getAllKeys",[IDBKeyRange.only(e)])).length)throw Error("Folder is not empty.");await this.dbRequest("delete",[e])}async mkdir(t){const e=this.getPath(t.directory,t.path),i=t.recursive,r=e.substr(0,e.lastIndexOf("/")),o=(e.match(/\//g)||[]).length,s=await this.dbRequest("get",[r]),a=await this.dbRequest("get",[e]);if(1===o)throw Error("Cannot create Root directory");if(void 0!==a)throw Error("Current directory does already exist.");if(!i&&2!==o&&void 0===s)throw Error("Parent directory must exist");if(i&&2!==o&&void 0===s){const e=r.substr(r.indexOf("/",1));await this.mkdir({path:e,directory:t.directory,recursive:i})}const n=Date.now(),d={path:e,folder:r,type:"directory",size:0,ctime:n,mtime:n};await this.dbRequest("put",[d])}async rmdir(t){const{path:e,directory:i,recursive:r}=t,o=this.getPath(i,e),s=await this.dbRequest("get",[o]);if(void 0===s)throw Error("Folder does not exist.");if("directory"!==s.type)throw Error("Requested path is not a directory");const a=await this.readdir({path:e,directory:i});if(0!==a.files.length&&!r)throw Error("Folder is not empty");for(const t of a.files){const o=`${e}/${t.name}`;"file"===(await this.stat({path:o,directory:i})).type?await this.deleteFile({path:o,directory:i}):await this.rmdir({path:o,directory:i,recursive:r})}await this.dbRequest("delete",[o])}async readdir(t){const e=this.getPath(t.directory,t.path),i=await this.dbRequest("get",[e]);if(""!==t.path&&void 0===i)throw Error("Folder does not exist.");const r=await this.dbIndexRequest("by_folder","getAllKeys",[IDBKeyRange.only(e)]);return{files:await Promise.all(r.map((async t=>{let i=await this.dbRequest("get",[t]);return void 0===i&&(i=await this.dbRequest("get",[t+"/"])),{name:t.substring(e.length+1),type:i.type,size:i.size,ctime:i.ctime,mtime:i.mtime,uri:i.path}})))}}async getUri(t){const e=this.getPath(t.directory,t.path);let i=await this.dbRequest("get",[e]);return void 0===i&&(i=await this.dbRequest("get",[e+"/"])),{uri:(null==i?void 0:i.path)||e}}async stat(t){const e=this.getPath(t.directory,t.path);let i=await this.dbRequest("get",[e]);if(void 0===i&&(i=await this.dbRequest("get",[e+"/"])),void 0===i)throw Error("Entry does not exist.");return{type:i.type,size:i.size,ctime:i.ctime,mtime:i.mtime,uri:i.path}}async rename(t){await this._copy(t,!0)}async copy(t){return this._copy(t,!1)}async requestPermissions(){return{publicStorage:"granted"}}async checkPermissions(){return{publicStorage:"granted"}}async _copy(t,i=!1){let{toDirectory:r}=t;const{to:o,from:s,directory:a}=t;if(!o||!s)throw Error("Both to and from must be provided");r||(r=a);const n=this.getPath(a,s),d=this.getPath(r,o);if(n===d)return{uri:d};if(function(t,i){t=e(t),i=e(i);const r=t.split("/"),o=i.split("/");return t!==i&&r.every(((t,e)=>t===o[e]))}(n,d))throw Error("To path cannot contain the from path");let c;try{c=await this.stat({path:o,directory:r})}catch(t){const e=o.split("/");e.pop();const i=e.join("/");if(e.length>0){if("directory"!==(await this.stat({path:i,directory:r})).type)throw new Error("Parent directory of the to path is a file")}}if(c&&"directory"===c.type)throw new Error("Cannot overwrite a directory with a file");const h=await this.stat({path:s,directory:a}),l=async(t,e,i)=>{const o=this.getPath(r,t),s=await this.dbRequest("get",[o]);s.ctime=e,s.mtime=i,await this.dbRequest("put",[s])},y=h.ctime?h.ctime:Date.now();switch(h.type){case"file":{const t=await this.readFile({path:s,directory:a});i&&await this.deleteFile({path:s,directory:a});const e=await this.writeFile({path:o,directory:r,data:t.data});return i&&await l(o,y,h.mtime),e}case"directory":{if(c)throw Error("Cannot move a directory over an existing object");try{await this.mkdir({path:o,directory:r,recursive:!1}),i&&await l(o,y,h.mtime)}catch(t){}const t=(await this.readdir({path:s,directory:a})).files;for(const e of t)await this._copy({from:`${s}/${e}`,to:`${o}/${e}`,directory:a,toDirectory:r},i);i&&await this.rmdir({path:s,directory:a})}}return{uri:d}}isBase64String(t){try{return btoa(atob(t))==t}catch(t){return!1}}}i._debug=!0;export{i as FilesystemWeb};
