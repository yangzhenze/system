package com.swsk.paypal;
import com.paypal.http.HttpClient;
import com.swsk.paypal.core.PayPalEnvironment;
import com.swsk.paypal.core.PayPalHttpClient;

public class TestHarness {

    private HttpClient client;
    private PayPalEnvironment environment;
    {
        setup();
    }

    public void setup() {
        environment = new PayPalEnvironment.Sandbox(
                "ATNuULnRo3dQONIyLtt9hirbIIgGYQnzcXOMrGlYQUKL3E_2uQ0JbvaetBOvKWpDqPZqB-ISi2ZIWWKQ",
                "EC8VdkJczzD2GdcFMGOyodfvL4TlgTpuDWOgtc5bO3tmJIQ4VF-YGwMmIhfNZ0itKaZjasKO7c0fO9YA");
        client = new PayPalHttpClient(environment);
    }

    protected HttpClient client() {
        return client;
    }

    protected PayPalEnvironment environment() {
        return environment;
    }
}
