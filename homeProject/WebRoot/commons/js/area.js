function Dsy()
{
this.Items = {};
}
Dsy.prototype.add = function(id,iArray)
{
this.Items[id] = iArray;
}
Dsy.prototype.Exists = function(id)
{
if(typeof(this.Items[id]) == "undefined") return false;
return true;
}

function change(v){
var str="0";
for(i=0;i<v;i++){ str+=("_"+(document.getElementById(s[i]).selectedIndex-1));};
var ss=document.getElementById(s[v]);
if (ss){
	with(ss){
		  length = 0;
		  options[0]=new Option(opt0[v],"");
		  if(v && document.getElementById(s[v-1]).selectedIndex>0 || !v)
		  {
		   if(dsy.Exists(str)){
		    ar = dsy.Items[str];
		    for(i=0;i<ar.length;i++)options[length]=new Option(ar[i],ar[i]);
		    if(v)options[1].selected = true;
		   }
		  }
		  if(++v<s.length){change(v);}
	}
}
}

var dsy = new Dsy();

dsy.add("0",["福州市","龙岩市","南平市","宁德市","莆田市","泉州市","三明市","厦门市","漳州市"]);
dsy.add("0_0",["鼓楼区","晋安区","台江区","仓山区","马尾区","长乐市","福清市","福州市","连江县","罗源县","闽侯县","闽清县","平潭县","永泰县"]);
dsy.add("0_1",["新罗区","长汀县","连城县","上杭县","武平县","永定县","漳平市"]);
dsy.add("0_2",["延平区","光泽县","建阳市","建瓯市","浦城县","邵武市","顺昌县","松溪县","武夷山市","政和县"]);
dsy.add("0_3",["宁德市","福安市","福鼎市","古田县","屏南县","寿宁县","霞浦县","周宁县","柘荣县"]);
dsy.add("0_4",["城厢区","涵江区", "仙游县","莆田县"]);
dsy.add("0_5",["鲤城区","丰泽区","洛江区","安溪县","德化县","惠安县","金门县","晋江市","南安市","泉州市","石狮市","永春县"]);
dsy.add("0_6",["三元区","梅列区","大田县","建宁县","将乐县","明溪县","宁化县","清流县","沙县","泰宁县","永安市","尤溪县"]);
dsy.add("0_7",["鼓浪屿区","思明区","湖里区","集美区","海沧区","开元区","同安区","杏林区"]);
dsy.add("0_8",["芗城区","龙文区","长泰县","东山县","华安县","龙海市","南靖县","平和县","云霄县","漳浦县","诏安县"]);


var s=["city","area"];
var opt0 = ["地级市","区、市、县"];
function setup()
{
for(i=0;i<s.length-1;i++)
  document.getElementById(s[i]).onchange=new Function("change("+(i+1)+")");
  change(0);
}

function fillArea(city){
	  var citys = dsy.Items["0"];
	  var areaStr;
	  for(var i=0;i<citys.length;i++)
		  if(citys[i] == city){
			  areaStr = "0_"+i;
			  break;
		  }
	  var areas = dsy.Items[areaStr];
	  if (areas){
		  for(i=0;i<areas.length;i++){
				 var newOption = new Option(areas[i],areas[i]);
				 $("#area")[0].add(newOption);
		  }
	  }
}