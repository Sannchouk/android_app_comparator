import{f as n}from"./main.js";function t(){return n("/tournament",{},!0)}function r(t){return n("/tournament/"+t,{query:{socketVersion:1,scores:0}},!0)}function e(t,r){return n("/tournament/"+t,{method:"GET",query:Object.assign({partial:1,scores:0},r?{page:r}:{})})}function o(t,r){return n("/tournament/"+t+"/standing/"+r,{query:{scores:0}})}function a(t,r,e){return n("/tournament/"+t+"/join",{method:"POST",body:JSON.stringify({p:r||null,team:e||null})},!0)}function u(t){return n("/tournament/"+t+"/withdraw",{method:"POST"},!0)}function i(t,r){return n("/tournament/"+t+"/player/"+r,{},!0)}function s(t,r,e,o,a,u,i,s,m,c){return n("/tournament/new",{method:"POST",body:JSON.stringify({name:t,variant:r,position:e,mode:o,clockTime:a,clockIncrement:u,minutes:i,waitMinutes:s,private:m,password:c})},!0)}export{t as a,s as c,a as j,o as l,i as p,e as r,r as t,u as w};