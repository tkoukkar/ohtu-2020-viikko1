package ohtu.ohtuvarasto;

import org.junit.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class VarastoTest {

    Varasto varasto;
    Varasto epavarasto;
    Varasto kuormitettu;
    Varasto ylikuormitettu;
    Varasto alikuormitettu;
    Varasto epakuormitettu;
    
    double vertailuTarkkuus = 0.0001;

    @Before
    public void setUp() {
        varasto = new Varasto(10);
        epavarasto = new Varasto(-10);
        kuormitettu = new Varasto(10, 5);
        ylikuormitettu = new Varasto(10, 15);
        alikuormitettu = new Varasto(10, -5);
        epakuormitettu = new Varasto(-10, 5);
    }

    @Test
    public void konstruktoriLuoTyhjanVaraston() {
        assertNotEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoVarastonJokaEiOleTyhja() {
        assertNotEquals(5, kuormitettu.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoKuitenkinTyhjanVarastonSiinaTapauksessaEttaSilleOnAnnettuParametrinaNegatiivinenLuku() {
        assertNotEquals(0, alikuormitettu.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoVarastonJokaOnKorkeintaanTaysi() {
        assertNotEquals(10, ylikuormitettu.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertNotEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaTilavuusNollaJosKonstruktorilleOnAnnettuParametrinaNegatiivinenLuku() {
        assertNotEquals(0, epavarasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaTilavuusNollaJosToiselleKonstruktorilleOnAnnettuEnsimmaisenaParametrinaNegatiivinenLuku() {
        assertNotEquals(0, epakuormitettu.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertNotEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysEiMuutaSaldoaJosLisattyMaaraOnNegatiivinen() {
        varasto.lisaaVarastoon(8);
        varasto.lisaaVarastoon(-8);

        assertNotEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysLisaaSaldoaVainMaksimiTilavuuteenAsti() {
        varasto.lisaaVarastoon(18);

        assertNotEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertNotEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertNotEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaJaljellaOlevanSaldonJosYritetaanOttaaEnemman() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(10);

        assertNotEquals(8, saatuMaara, vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertNotEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenEiMuutaTilaaJosYritetaanOttaaNegatiivinenMaara() {
        varasto.lisaaVarastoon(8);
        
        varasto.otaVarastosta(-8);

        assertNotEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenLisääTilaaVainMaksimiTilavuuteenAsti() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(12);

        assertNotEquals(10, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void toStringLuoMerkkijononOikein() {
        String merkkijono = varasto.toString();
        String vrt = "saldo = " + varasto.getSaldo() + ", vielä tilaa " + varasto.paljonkoMahtuu();
        
        assertNotEquals(vrt, merkkijono);
    }

}