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
        assertEquals(0, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoVarastonJokaEiOleTyhja() {
        assertEquals(5, kuormitettu.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoKuitenkinTyhjanVarastonSiinaTapauksessaEttaSilleOnAnnettuParametrinaNegatiivinenLuku() {
        assertEquals(0, alikuormitettu.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void toinenKonstruktoriLuoVarastonJokaOnKorkeintaanTaysi() {
        assertEquals(10, ylikuormitettu.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void uudellaVarastollaOikeaTilavuus() {
        assertEquals(10, varasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaTilavuusNollaJosKonstruktorilleOnAnnettuParametrinaNegatiivinenLuku() {
        assertEquals(0, epavarasto.getTilavuus(), vertailuTarkkuus);
    }
    
    @Test
    public void uudellaVarastollaTilavuusNollaJosToiselleKonstruktorilleOnAnnettuEnsimmaisenaParametrinaNegatiivinenLuku() {
        assertEquals(0, epakuormitettu.getTilavuus(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaSaldoa() {
        varasto.lisaaVarastoon(8);

        // saldon pitäisi olla sama kun lisätty määrä
        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysEiMuutaSaldoaJosLisattyMaaraOnNegatiivinen() {
        varasto.lisaaVarastoon(8);
        varasto.lisaaVarastoon(-8);

        assertEquals(8, varasto.getSaldo(), vertailuTarkkuus);
    }
    
    @Test
    public void lisaysLisaaSaldoaVainMaksimiTilavuuteenAsti() {
        varasto.lisaaVarastoon(18);

        assertEquals(10, varasto.getSaldo(), vertailuTarkkuus);
    }

    @Test
    public void lisaysLisaaPienentaaVapaataTilaa() {
        varasto.lisaaVarastoon(8);

        // vapaata tilaa pitäisi vielä olla tilavuus-lisättävä määrä eli 2
        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaOikeanMaaran() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(2);

        assertEquals(2, saatuMaara, vertailuTarkkuus);
    }

    @Test
    public void ottaminenPalauttaaJaljellaOlevanSaldonJosYritetaanOttaaEnemman() {
        varasto.lisaaVarastoon(8);

        double saatuMaara = varasto.otaVarastosta(10);

        assertEquals(8, saatuMaara, vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenLisääTilaa() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(2);

        // varastossa pitäisi olla tilaa 10 - 8 + 2 eli 4
        assertEquals(4, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenEiMuutaTilaaJosYritetaanOttaaNegatiivinenMaara() {
        varasto.lisaaVarastoon(8);
        
        varasto.otaVarastosta(-8);

        assertEquals(2, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void ottaminenLisääTilaaVainMaksimiTilavuuteenAsti() {
        varasto.lisaaVarastoon(8);

        varasto.otaVarastosta(12);

        assertEquals(10, varasto.paljonkoMahtuu(), vertailuTarkkuus);
    }
    
    @Test
    public void toStringLuoMerkkijononOikein() {
        String merkkijono = varasto.toString();
        String vrt = "saldo = " + varasto.getSaldo() + ", vielä tilaa " + varasto.paljonkoMahtuu();
        
        assertEquals(vrt, merkkijono);
    }

}