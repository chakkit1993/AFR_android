package com.apitech.lambda_sensor.modelData;

public class DataMonitor {

    int adcValue_UA = 0;                                                /* ADC value read from the CJ125 UA output pin */
    int adcValue_UR = 0;                                                /* ADC value read from the CJ125 UR output pin */
    int adcValue_UB = 0;                                                /* ADC value read from the voltage divider caluclating Ubat */

    public int getAdcValue_DAC() {
        return adcValue_DAC;
    }

    public void setAdcValue_DAC(int adcValue_DAC) {
        this.adcValue_DAC = adcValue_DAC;
    }

    int adcValue_DAC = 0;

    float LAMBDA_VALUE = 0;
    float OXYGEN_CONTENT  = 0;
    float SupplyVoltage= 0 ;


    public String getCJ125_Status() {
        return CJ125_Status;
    }

    public void setCJ125_Status(String CJ125_Status) {
        this.CJ125_Status = CJ125_Status;
    }

    public float getIp_mA() {
        return Ip_mA;
    }

    public void setIp_mA(float ip_mA) {
        Ip_mA = ip_mA;
    }

    String CJ125_Status;
    float Ip_mA  = 0;



    public DataMonitor() {
        this.adcValue_UA = adcValue_UA;
        this.adcValue_UR = adcValue_UR;
        this.adcValue_UB = adcValue_UB;
        this.adcValue_DAC = adcValue_DAC;
        this.LAMBDA_VALUE = LAMBDA_VALUE;
        this.OXYGEN_CONTENT = OXYGEN_CONTENT;
        this.SupplyVoltage = SupplyVoltage;
    }

    public DataMonitor(int adcValue_UA, int adcValue_UR, int adcValue_UB, int adcValue_DAC ,float LAMBDA_VALUE, float OXYGEN_CONTENT, float SupplyVoltage) {
        this.adcValue_UA = adcValue_UA;
        this.adcValue_UR = adcValue_UR;
        this.adcValue_UB = adcValue_UB;
        this.adcValue_DAC = adcValue_DAC;
        this.LAMBDA_VALUE = LAMBDA_VALUE;
        this.OXYGEN_CONTENT = OXYGEN_CONTENT;
        this.SupplyVoltage = SupplyVoltage;
    }

    public int getAdcValue_UA() {
        return adcValue_UA;
    }





    public void setAdcValue_UA(int adcValue_UA) {
        this.adcValue_UA = adcValue_UA;
    }

    public int getAdcValue_UR() {
        return adcValue_UR;
    }

    public void setAdcValue_UR(int adcValue_UR) {
        this.adcValue_UR = adcValue_UR;
    }

    public int getAdcValue_UB() {
        return adcValue_UB;
    }

    public void setAdcValue_UB(int adcValue_UB) {
        this.adcValue_UB = adcValue_UB;
    }

    public double getAdcVolt_UA() {
        return (adcValue_UA * 5) / 4095.0;
    }
    public double getAdcVolt_UR() {
        return (adcValue_UR * 5) / 4095.0;
    }
    public double getAdcVolt_UB() {
        return (adcValue_UB * 16 * 1.3) / 4095.0;
    }
    public double getAdcVolt_DAC() {
        return (adcValue_DAC * 5) / 4095.0;
    }

    public float getLAMBDA_VALUE() {
        return LAMBDA_VALUE;
    }

    public void setLAMBDA_VALUE(float LAMBDA_VALUE) {
        this.LAMBDA_VALUE = LAMBDA_VALUE;
    }

    public float getOXYGEN_CONTENT() {
        return OXYGEN_CONTENT;
    }

    public void setOXYGEN_CONTENT(float OXYGEN_CONTENT) {
        this.OXYGEN_CONTENT = OXYGEN_CONTENT;
    }

    public float getSupplyVoltage() {
        return SupplyVoltage;
    }

    public void setSupplyVoltage(float supplyVoltage) {
        SupplyVoltage = supplyVoltage;
    }
}
