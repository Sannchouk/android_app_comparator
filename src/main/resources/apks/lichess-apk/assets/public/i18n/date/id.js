var a={lessThanXSeconds:{one:"kurang dari 1 detik",other:"kurang dari {{count}} detik"},xSeconds:{one:"1 detik",other:"{{count}} detik"},halfAMinute:"setengah menit",lessThanXMinutes:{one:"kurang dari 1 menit",other:"kurang dari {{count}} menit"},xMinutes:{one:"1 menit",other:"{{count}} menit"},aboutXHours:{one:"sekitar 1 jam",other:"sekitar {{count}} jam"},xHours:{one:"1 jam",other:"{{count}} jam"},xDays:{one:"1 hari",other:"{{count}} hari"},aboutXWeeks:{one:"sekitar 1 minggu",other:"sekitar {{count}} minggu"},xWeeks:{one:"1 minggu",other:"{{count}} minggu"},aboutXMonths:{one:"sekitar 1 bulan",other:"sekitar {{count}} bulan"},xMonths:{one:"1 bulan",other:"{{count}} bulan"},aboutXYears:{one:"sekitar 1 tahun",other:"sekitar {{count}} tahun"},xYears:{one:"1 tahun",other:"{{count}} tahun"},overXYears:{one:"lebih dari 1 tahun",other:"lebih dari {{count}} tahun"},almostXYears:{one:"hampir 1 tahun",other:"hampir {{count}} tahun"}};function e(a){return function(e){var t=e||{},n=t.width?String(t.width):a.defaultWidth;return a.formats[n]||a.formats[a.defaultWidth]}}var t={date:e({formats:{full:"EEEE, d MMMM yyyy",long:"d MMMM yyyy",medium:"d MMM yyyy",short:"d/M/yyyy"},defaultWidth:"full"}),time:e({formats:{full:"HH.mm.ss",long:"HH.mm.ss",medium:"HH.mm",short:"HH.mm"},defaultWidth:"full"}),dateTime:e({formats:{full:"{{date}} 'pukul' {{time}}",long:"{{date}} 'pukul' {{time}}",medium:"{{date}}, {{time}}",short:"{{date}}, {{time}}"},defaultWidth:"full"})},n={lastWeek:"eeee 'lalu pukul' p",yesterday:"'Kemarin pukul' p",today:"'Hari ini pukul' p",tomorrow:"'Besok pukul' p",nextWeek:"eeee 'pukul' p",other:"P"};function i(a){return function(e,t){var n,i=t||{};if("formatting"===(i.context?String(i.context):"standalone")&&a.formattingValues){var r=a.defaultFormattingWidth||a.defaultWidth,u=i.width?String(i.width):r;n=a.formattingValues[u]||a.formattingValues[r]}else{var o=a.defaultWidth,m=i.width?String(i.width):a.defaultWidth;n=a.values[m]||a.values[o]}return n[a.argumentCallback?a.argumentCallback(e):e]}}function r(a){return function(e,t){var n=String(e),i=t||{},r=i.width,u=r&&a.matchPatterns[r]||a.matchPatterns[a.defaultMatchWidth],o=n.match(u);if(!o)return null;var m,l=o[0],s=r&&a.parsePatterns[r]||a.parsePatterns[a.defaultParseWidth];return m="[object Array]"===Object.prototype.toString.call(s)?function(a,e){for(var t=0;t<a.length;t++)if(e(a[t]))return t}(s,(function(a){return a.test(l)})):function(a,e){for(var t in a)if(a.hasOwnProperty(t)&&e(a[t]))return t}(s,(function(a){return a.test(l)})),m=a.valueCallback?a.valueCallback(m):m,{value:m=i.valueCallback?i.valueCallback(m):m,rest:n.slice(l.length)}}}var u,o={code:"id",formatDistance:function(e,t,n){var i;return n=n||{},i="string"==typeof a[e]?a[e]:1===t?a[e].one:a[e].other.replace("{{count}}",t),n.addSuffix?n.comparison>0?"dalam waktu "+i:i+" yang lalu":i},formatLong:t,formatRelative:function(a,e,t,i){return n[a]},localize:{ordinalNumber:function(a,e){var t=Number(a);return"ke-"+t},era:i({values:{narrow:["SM","M"],abbreviated:["SM","M"],wide:["Sebelum Masehi","Masehi"]},defaultWidth:"wide"}),quarter:i({values:{narrow:["1","2","3","4"],abbreviated:["K1","K2","K3","K4"],wide:["Kuartal ke-1","Kuartal ke-2","Kuartal ke-3","Kuartal ke-4"]},defaultWidth:"wide",argumentCallback:function(a){return Number(a)-1}}),month:i({values:{narrow:["J","F","M","A","M","J","J","A","S","O","N","D"],abbreviated:["Jan","Feb","Mar","Apr","Mei","Jun","Jul","Ags","Sep","Okt","Nov","Des"],wide:["Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","November","Desember"]},defaultWidth:"wide"}),day:i({values:{narrow:["M","S","S","R","K","J","S"],short:["Min","Sen","Sel","Rab","Kam","Jum","Sab"],abbreviated:["Min","Sen","Sel","Rab","Kam","Jum","Sab"],wide:["Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"]},defaultWidth:"wide"}),dayPeriod:i({values:{narrow:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"},abbreviated:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"},wide:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"}},defaultWidth:"wide",formattingValues:{narrow:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"},abbreviated:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"},wide:{am:"AM",pm:"PM",midnight:"tengah malam",noon:"tengah hari",morning:"pagi",afternoon:"siang",evening:"sore",night:"malam"}},defaultFormattingWidth:"wide"})},match:{ordinalNumber:(u={matchPattern:/^ke-(\d+)?/i,parsePattern:/\d+/i,valueCallback:function(a){return parseInt(a,10)}},function(a,e){var t=String(a),n=e||{},i=t.match(u.matchPattern);if(!i)return null;var r=i[0],o=t.match(u.parsePattern);if(!o)return null;var m=u.valueCallback?u.valueCallback(o[0]):o[0];return{value:m=n.valueCallback?n.valueCallback(m):m,rest:t.slice(r.length)}}),era:r({matchPatterns:{narrow:/^(sm|m)/i,abbreviated:/^(s\.?\s?m\.?|s\.?\s?e\.?\s?u\.?|m\.?|e\.?\s?u\.?)/i,wide:/^(sebelum masehi|sebelum era umum|masehi|era umum)/i},defaultMatchWidth:"wide",parsePatterns:{any:[/^s/i,/^(m|e)/i]},defaultParseWidth:"any"}),quarter:r({matchPatterns:{narrow:/^[1234]/i,abbreviated:/^K-?\s[1234]/i,wide:/^Kuartal ke-?\s?[1234]/i},defaultMatchWidth:"wide",parsePatterns:{any:[/1/i,/2/i,/3/i,/4/i]},defaultParseWidth:"any",valueCallback:function(a){return a+1}}),month:r({matchPatterns:{narrow:/^[jfmasond]/i,abbreviated:/^(jan|feb|mar|apr|mei|jun|jul|agt|sep|okt|nov|des)/i,wide:/^(januari|februari|maret|april|mei|juni|juli|agustus|september|oktober|november|desember)/i},defaultMatchWidth:"wide",parsePatterns:{narrow:[/^j/i,/^f/i,/^m/i,/^a/i,/^m/i,/^j/i,/^j/i,/^a/i,/^s/i,/^o/i,/^n/i,/^d/i],any:[/^ja/i,/^f/i,/^ma/i,/^ap/i,/^me/i,/^jun/i,/^jul/i,/^ag/i,/^s/i,/^o/i,/^n/i,/^d/i]},defaultParseWidth:"any"}),day:r({matchPatterns:{narrow:/^[srkjm]/i,short:/^(min|sen|sel|rab|kam|jum|sab)/i,abbreviated:/^(min|sen|sel|rab|kam|jum|sab)/i,wide:/^(minggu|senin|selasa|rabu|kamis|jumat|sabtu)/i},defaultMatchWidth:"wide",parsePatterns:{narrow:[/^m/i,/^s/i,/^s/i,/^r/i,/^k/i,/^j/i,/^s/i],any:[/^m/i,/^sen/i,/^sel/i,/^r/i,/^k/i,/^j/i,/^sa/i]},defaultParseWidth:"any"}),dayPeriod:r({matchPatterns:{narrow:/^(a|p|tengah m|tengah h|(di(\swaktu)?) (pagi|siang|sore|malam))/i,any:/^([ap]\.?\s?m\.?|tengah malam|tengah hari|(di(\swaktu)?) (pagi|siang|sore|malam))/i},defaultMatchWidth:"any",parsePatterns:{any:{am:/^a/i,pm:/^pm/i,midnight:/^tengah m/i,noon:/^tengah h/i,morning:/pagi/i,afternoon:/siang/i,evening:/sore/i,night:/malam/i}},defaultParseWidth:"any"})},options:{weekStartsOn:1,firstWeekContainsDate:1}};export default o;