# appGijon

This is a collaborative project to extend the data currently offered in the app from Gijon city's catalogue.

As Gijon's city council does not offer an online service to retrieve their catalogue, every single resource has to be implemented manually. In order to make the implementation of different resources as intuitive as posible, the app has an architecture base in the <b><i>strategy</i></b> design pattern. 

There is a basic implementation that can be applied for most of the JSON resources currently offered. 

This are the steps needed to include a new resource from the  <a href="https://transparencia.gijon.es/page/1808-catalogo-de-datos">catalogue</a>. 


<h2>Example:</h2>

<h5>In case we want to add a new resource we would need to create a new class like the following. Where the only things that need to be included are the URL (required) of the JSON resource and and icon and marker in case we want to.  </h5>

<pre> 
public class CasinoSource extends JSONResource {
    private static final String TAG = "CasinoSource";

    public CasinoSource() {
        setIcon(R.drawable.casino_icon);     // Optional
        setMarker(R.drawable.casino_marker); // Optional: Size has to be 25x25
        setURL(URLConstants.CASINO_SOURCE_URL);
    }

    public CasinoSource(Context context, ProgressDialog progressDialog) {
        this();
        this.context = context;
        this.progressDialog = progressDialog;
    }

    public CasinoSource(Context context) {
        new CasinoSource(context, null);
    }
}
</pre>

<h5>Next step would be to include it in the list of products offered within the <b>ServiceListActivity.java</b>. The only thing that needs to be done is adding a new item to the <b><i>classMap</i></b></h5>

<pre>
  {
      .
      .
      .
      put("Oil Deposits", OilDepositSource.class);
      put("Twitter", null);
      <b><i>put("Casinos", CasinoSource.class);</i></b>
  }
</pre>


Once we've finished adding this, we would be able to run the app and check how does the new resource look in our map.
