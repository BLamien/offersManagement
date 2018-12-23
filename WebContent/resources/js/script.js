
/*ajouter dossier*/
function onChangeDossier(dossier){
    switch (dossier.value){
        case "cps":
               $("#typecpsblock").show();
               forlaison("h");
                break;
        case "liaison":
                forlaison("s");
                $("#typecpsblock").hide();
                break;
        default:
               $("#typecpsblock").hide();
               forlaison("h");
                break;
    }
    function forlaison(vl)
    {
       $('.forlaison').each(function(i,v){
           if(vl==="h")
                $(v).hide();
            else if(vl==="s")
                $(v).show();
            });
    }
}
/*recherche avancee*/
function toogleRechercheAv()
{
    $(".panleRechercheAv").toggle("blind");
}
function clonMot()
{
        $(".cloned").first().attr("class","").show(); 
}
var clicked=false;
function changeMenu()
{
   if(clicked===true)
   {
       beSmallMenu();
       clicked=false;
   }else
   {
       beLargeMenu();
       clicked=true;
   }
}
beSmallMenu();
function beSmallMenu()
{   
    $(".left").css("width","4%");
    $(".right").css("width","96%");
    //$(".left").removeClass(".largemenuleft").addClass(".smallmenuleft");
    //$(".right").removeClass(".smallmenuright").addClass(".largemenuright");
    $(".rechercheinputdiv").hide();
    $(".rehercheavtoogle").text("").append($('<i class="fa fa-search" aria-hidden="true" style="font-size:15px;margin-top:5px;" >'));
    $(".itemlr").text("LR").css({"color":"#92B613"});
    $(".itemcr").text("CR").css({"color":"#92B613"});
    $(".itemoa").text("OA").css({"color":"#92B613"});
    $(".itemcpse").text("CPSE").css({"color":"#92B613","margin-left":"-5px"});
    $(".itemcpst").text("CPST").css({"color":"#92B613","margin-left":"-5px"});
    $(".itemcpsc").text("CPSC").css({"color":"#92B613","margin-left":"-5px"});
}
function beLargeMenu()
{
    $(".left").css("width","16%");
    $(".right").css("width","84%");
    //$(".left").removeClass(".smallmenuleft").addClass(".largemenuleft");
    //$(".right").removeClass(".largemenuright").addClass(".smallmenuright");
    $(".rechercheinputdiv").show();
    $(".rehercheavtoogle").text("Recherche Avancée");
     $(".itemlr").text("Liaison routière").css("color","#D9D9D9");
    $(".itemcr").text("Carrefour").css("color","#D9D9D9");
    $(".itemoa").text("OA").css("color","#D9D9D9");
    $(".itemcpse").text("CPS Etude").css("color","#D9D9D9");
    $(".itemcpst").text("CPS Travraux").css("color","#D9D9D9");
    $(".itemcpsc").text("CPS Controle").css("color","#D9D9D9");
    /*$(".itemlr").text("LR")
    $(".itemcr").text("CR").css("color","#92B613").css( "font-family","Segoe UI").css("font-weight","bold");
    $(".itemoa").text("OA").css("color","#92B613").css( "font-family","Segoe UI").css("font-weight","bold");
    $(".itemcpse").text("CPSE").css("color","#92B613").css( "font-family","Segoe UI").css("font-weight","bold");
    $(".itemcpst").text("CPST").css("color","#92B613").css( "font-family","Segoe UI").css("font-weight","bold");
    $(".itemcpsc").text("CPSC").css("color","#92B613").css( "font-family","Segoe UI").css("font-weight","bold");*/
}
$(".deleteMot").click(function(){
    $(this).parent("td").parent("tr").hide();
});
etat=true;
function pramaffiche(param)
        { 
            dot=".";
            colmn=dot.concat($(param).val());
                       if (etat) {
                            $(colmn).hide();
                            etat=false;
                        }else{
                            $(colmn).show();
                            etat=true;
                        }
            
        }
