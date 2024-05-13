function t(t,e,a){var n=function(t,e){return 1===e?t.one:e>=2&&e<=4?t.twoFour:t.other}(t,e);return(n[a]||n).replace("{{count}}",e)}function e(t){var e="";return"almost"===t&&(e="takmer"),"about"===t&&(e="približne"),e.length>0?e+" ":""}function a(t){var e="";return"lessThan"===t&&(e="menej než"),"over"===t&&(e="viac než"),e.length>0?e+" ":""}var n={xSeconds:{one:{regular:"sekunda",past:"sekundou",future:"sekundu"},twoFour:{regular:"{{count}} sekundy",past:"{{count}} sekundami",future:"{{count}} sekundy"},other:{regular:"{{count}} sekúnd",past:"{{count}} sekundami",future:"{{count}} sekúnd"}},halfAMinute:{other:{regular:"pol minúty",past:"pol minútou",future:"pol minúty"}},xMinutes:{one:{regular:"minúta",past:"minútou",future:"minútu"},twoFour:{regular:"{{count}} minúty",past:"{{count}} minútami",future:"{{count}} minúty"},other:{regular:"{{count}} minút",past:"{{count}} minútami",future:"{{count}} minút"}},xHours:{one:{regular:"hodina",past:"hodinou",future:"hodinu"},twoFour:{regular:"{{count}} hodiny",past:"{{count}} hodinami",future:"{{count}} hodiny"},other:{regular:"{{count}} hodín",past:"{{count}} hodinami",future:"{{count}} hodín"}},xDays:{one:{regular:"deň",past:"dňom",future:"deň"},twoFour:{regular:"{{count}} dni",past:"{{count}} dňami",future:"{{count}} dni"},other:{regular:"{{count}} dní",past:"{{count}} dňami",future:"{{count}} dní"}},xWeeks:{one:{regular:"mesiac",past:"mesiacom",future:"mesiac"},twoFour:{regular:"{{count}} mesiace",past:"{{count}} mesiacmi",future:"{{count}} mesiace"},other:{regular:"{{count}} mesiacov",past:"{{count}} mesiacmi",future:"{{count}} mesiacov"}},xMonths:{one:{regular:"mesiac",past:"mesiacom",future:"mesiac"},twoFour:{regular:"{{count}} mesiace",past:"{{count}} mesiacmi",future:"{{count}} mesiace"},other:{regular:"{{count}} mesiacov",past:"{{count}} mesiacmi",future:"{{count}} mesiacov"}},xYears:{one:{regular:"rok",past:"rokom",future:"rok"},twoFour:{regular:"{{count}} roky",past:"{{count}} rokmi",future:"{{count}} roky"},other:{regular:"{{count}} rokov",past:"{{count}} rokmi",future:"{{count}} rokov"}}};function r(t){return function(e){var a=e||{},n=a.width?String(a.width):t.defaultWidth;return t.formats[n]||t.formats[t.defaultWidth]}}var o={date:r({formats:{full:"EEEE d. MMMM y",long:"d. MMMM y",medium:"d. M. y",short:"d. M. y"},defaultWidth:"full"}),time:r({formats:{full:"H:mm:ss zzzz",long:"H:mm:ss z",medium:"H:mm:ss",short:"H:mm"},defaultWidth:"full"}),dateTime:r({formats:{full:"{{date}}, {{time}}",long:"{{date}}, {{time}}",medium:"{{date}}, {{time}}",short:"{{date}} {{time}}"},defaultWidth:"full"})};function i(t){if(null===t||!0===t||!1===t)return NaN;var e=Number(t);return isNaN(e)?e:e<0?Math.ceil(e):Math.floor(e)}function u(t,e){if(e.length<t)throw new TypeError(t+" argument"+(t>1?"s":"")+" required, but only "+e.length+" present")}function s(t){u(1,arguments);var e=Object.prototype.toString.call(t);return t instanceof Date||"object"==typeof t&&"[object Date]"===e?new Date(t.getTime()):"number"==typeof t||"[object Number]"===e?new Date(t):("string"!=typeof t&&"[object String]"!==e||"undefined"==typeof console||(console.warn("Starting with v2.0.0-beta.1 date-fns doesn't accept strings as arguments. Please use `parseISO` to parse strings. See: https://git.io/fjule"),console.warn((new Error).stack)),new Date(NaN))}function l(t,e){u(1,arguments);var a=e||{},n=a.locale,r=n&&n.options&&n.options.weekStartsOn,o=null==r?0:i(r),l=null==a.weekStartsOn?o:i(a.weekStartsOn);if(!(l>=0&&l<=6))throw new RangeError("weekStartsOn must be between 0 and 6 inclusively");var d=s(t),c=d.getUTCDay(),m=(c<l?7:0)+c-l;return d.setUTCDate(d.getUTCDate()-m),d.setUTCHours(0,0,0,0),d}function d(t,e,a){u(2,arguments);var n=l(t,a),r=l(e,a);return n.getTime()===r.getTime()}var c=["nedeľu","pondelok","utorok","stredu","štvrtok","piatok","sobotu"];function m(t){return 4===t?"'vo' eeee 'o' p":"'v "+c[t]+" o' p"}var p={lastWeek:function(t,e,a){var n=t.getUTCDay();return d(t,e,a)?m(n):function(t){var e=c[t];switch(t){case 0:case 3:case 6:return"'minulú "+e+" o' p";default:return"'minulý' eeee 'o' p"}}(n)},yesterday:"'včera o' p",today:"'dnes o' p",tomorrow:"'zajtra o' p",nextWeek:function(t,e,a){var n=t.getUTCDay();return d(t,e,a)?m(n):function(t){var e=c[t];switch(t){case 0:case 4:case 6:return"'budúcu' "+e+" 'o' p";default:return"'budúci' eeee 'o' p"}}(n)},other:"P"};function f(t){return function(e,a){var n,r=a||{};if("formatting"===(r.context?String(r.context):"standalone")&&t.formattingValues){var o=t.defaultFormattingWidth||t.defaultWidth,i=r.width?String(r.width):o;n=t.formattingValues[i]||t.formattingValues[o]}else{var u=t.defaultWidth,s=r.width?String(r.width):t.defaultWidth;n=t.values[s]||t.values[u]}return n[t.argumentCallback?t.argumentCallback(e):e]}}function v(t){return function(e,a){var n=String(e),r=a||{},o=r.width,i=o&&t.matchPatterns[o]||t.matchPatterns[t.defaultMatchWidth],u=n.match(i);if(!u)return null;var s,l=u[0],d=o&&t.parsePatterns[o]||t.parsePatterns[t.defaultParseWidth];return s="[object Array]"===Object.prototype.toString.call(d)?function(t,e){for(var a=0;a<t.length;a++)if(e(t[a]))return a}(d,(function(t){return t.test(l)})):function(t,e){for(var a in t)if(t.hasOwnProperty(a)&&e(t[a]))return a}(d,(function(t){return t.test(l)})),s=t.valueCallback?t.valueCallback(s):s,{value:s=r.valueCallback?r.valueCallback(s):s,rest:n.slice(l.length)}}}var h,g={code:"sk",formatDistance:function(r,o,i){i=i||{};var u,s=function(t){return["lessThan","about","over","almost"].filter((function(e){return!!t.match(new RegExp("^"+e))}))[0]}(r)||"",l=(u=r.substring(s.length)).charAt(0).toLowerCase()+u.slice(1),d=n[l];return i.addSuffix?i.comparison>0?e(s)+"o "+a(s)+t(d,o,"future"):e(s)+"pred "+a(s)+t(d,o,"past"):e(s)+a(s)+t(d,o,"regular")},formatLong:o,formatRelative:function(t,e,a,n){var r=p[t];return"function"==typeof r?r(e,a,n):r},localize:{ordinalNumber:function(t,e){return Number(t)+"."},era:f({values:{narrow:["pred Kr.","po Kr."],abbreviated:["pred Kr.","po Kr."],wide:["pred Kristom","po Kristovi"]}}),quarter:f({values:{narrow:["1","2","3","4"],abbreviated:["Q1","Q2","Q3","Q4"],wide:["1. štvrťrok","2. štvrťrok","3. štvrťrok","4. štvrťrok"]},defaultWidth:"wide",argumentCallback:function(t){return Number(t)-1}}),month:f({values:{narrow:["j","f","m","a","m","j","j","a","s","o","n","d"],abbreviated:["jan","feb","mar","apr","máj","jún","júl","aug","sep","okt","nov","dec"],wide:["január","február","marec","apríl","máj","jún","júl","august","september","október","november","december"]},defaultWidth:"wide",formattingValues:{narrow:["j","f","m","a","m","j","j","a","s","o","n","d"],abbreviated:["jan","feb","mar","apr","máj","jún","júl","aug","sep","okt","nov","dec"],wide:["januára","februára","marca","apríla","mája","júna","júla","augusta","septembra","októbra","novembra","decembra"]},defaultFormattingWidth:"wide"}),day:f({values:{narrow:["n","p","u","s","š","p","s"],short:["ne","po","ut","st","št","pi","so"],abbreviated:["ne","po","ut","st","št","pi","so"],wide:["nedeľa","pondelok","utorok","streda","štvrtok","piatok","sobota"]},defaultWidth:"wide"}),dayPeriod:f({values:{narrow:{am:"AM",pm:"PM",midnight:"poln.",noon:"pol.",morning:"ráno",afternoon:"pop.",evening:"več.",night:"noc"},abbreviated:{am:"AM",pm:"PM",midnight:"poln.",noon:"pol.",morning:"ráno",afternoon:"popol.",evening:"večer",night:"noc"},wide:{am:"AM",pm:"PM",midnight:"polnoc",noon:"poludnie",morning:"ráno",afternoon:"popoludnie",evening:"večer",night:"noc"}},defaultWidth:"wide",formattingValues:{narrow:{am:"AM",pm:"PM",midnight:"o poln.",noon:"nap.",morning:"ráno",afternoon:"pop.",evening:"več.",night:"v n."},abbreviated:{am:"AM",pm:"PM",midnight:"o poln.",noon:"napol.",morning:"ráno",afternoon:"popol.",evening:"večer",night:"v noci"},wide:{am:"AM",pm:"PM",midnight:"o polnoci",noon:"napoludnie",morning:"ráno",afternoon:"popoludní",evening:"večer",night:"v noci"}},defaultFormattingWidth:"wide"})},match:{ordinalNumber:(h={matchPattern:/^(\d+)\.?/i,parsePattern:/\d+/i,valueCallback:function(t){return parseInt(t,10)}},function(t,e){var a=String(t),n=e||{},r=a.match(h.matchPattern);if(!r)return null;var o=r[0],i=a.match(h.parsePattern);if(!i)return null;var u=h.valueCallback?h.valueCallback(i[0]):i[0];return{value:u=n.valueCallback?n.valueCallback(u):u,rest:a.slice(o.length)}}),era:v({matchPatterns:{narrow:/^(pred Kr\.|pred n\. l\.|po Kr\.|n\. l\.)/i,abbreviated:/^(pred Kr\.|pred n\. l\.|po Kr\.|n\. l\.)/i,wide:/^(pred Kristom|pred na[šs][íi]m letopo[čc]tom|po Kristovi|n[áa][šs]ho letopo[čc]tu)/i},defaultMatchWidth:"wide",parsePatterns:{any:[/^pr/i,/^(po|n)/i]},defaultParseWidth:"any"}),quarter:v({matchPatterns:{narrow:/^[1234]/i,abbreviated:/^q[1234]/i,wide:/^[1234]\. [šs]tvr[ťt]rok/i},defaultMatchWidth:"wide",parsePatterns:{any:[/1/i,/2/i,/3/i,/4/i]},defaultParseWidth:"any",valueCallback:function(t){return t+1}}),month:v({matchPatterns:{narrow:/^[jfmasond]/i,abbreviated:/^(jan|feb|mar|apr|m[áa]j|j[úu]n|j[úu]l|aug|sep|okt|nov|dec)/i,wide:/^(janu[áa]ra?|febru[áa]ra?|(marec|marca)|apr[íi]la?|m[áa]ja?|j[úu]na?|j[úu]la?|augusta?|(september|septembra)|(okt[óo]ber|okt[óo]bra)|(november|novembra)|(december|decembra))/i},defaultMatchWidth:"wide",parsePatterns:{narrow:[/^j/i,/^f/i,/^m/i,/^a/i,/^m/i,/^j/i,/^j/i,/^a/i,/^s/i,/^o/i,/^n/i,/^d/i],any:[/^ja/i,/^f/i,/^mar/i,/^ap/i,/^m[áa]j/i,/^j[úu]n/i,/^j[úu]l/i,/^au/i,/^s/i,/^o/i,/^n/i,/^d/i]},defaultParseWidth:"any"}),day:v({matchPatterns:{narrow:/^[npusšp]/i,short:/^(ne|po|ut|st|št|pi|so)/i,abbreviated:/^(ne|po|ut|st|št|pi|so)/i,wide:/^(nede[ľl]a|pondelok|utorok|streda|[šs]tvrtok|piatok|sobota])/i},defaultMatchWidth:"wide",parsePatterns:{narrow:[/^n/i,/^p/i,/^u/i,/^s/i,/^š/i,/^p/i,/^s/i],any:[/^n/i,/^po/i,/^u/i,/^st/i,/^(št|stv)/i,/^pi/i,/^so/i]},defaultParseWidth:"any"}),dayPeriod:v({matchPatterns:{narrow:/^(am|pm|(o )?poln\.?|(nap\.?|pol\.?)|r[áa]no|pop\.?|ve[čc]\.?|(v n\.?|noc))/i,abbreviated:/^(am|pm|(o )?poln\.?|(napol\.?|pol\.?)|r[áa]no|pop\.?|ve[čc]er|(v )?noci?)/i,any:/^(am|pm|(o )?polnoci?|(na)?poludnie|r[áa]no|popoludn(ie|í|i)|ve[čc]er|(v )?noci?)/i},defaultMatchWidth:"any",parsePatterns:{any:{am:/^am/i,pm:/^pm/i,midnight:/poln/i,noon:/^(nap|(na)?pol(\.|u))/i,morning:/^r[áa]no/i,afternoon:/^pop/i,evening:/^ve[čc]/i,night:/^(noc|v n\.)/i}},defaultParseWidth:"any"})},options:{weekStartsOn:1,firstWeekContainsDate:4}};export default g;