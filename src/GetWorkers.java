import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.redbottledesign.drupal.Node;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;
import com.redbottledesign.drupal.gson.requestor.NodeRequestor;

public class GetWorkers
{
  protected static final String DRUPAL_USER_NAME = "Pool manager daemon";
  protected static final String DRUPAL_PASSWORD  = "bxhaYDnrAVbAraeBPMIRhutcfDKIanv3AnIH7Skb83Jzci6yuUbsSmIuhSwLAGhn";

  public static void main(String[] args)
  throws URISyntaxException, IOException, DrupalHttpException
  {
    // bxhaYDnrAVbAraeBPMIRhutcfDKIanv3AnIH7Skb83Jzci6yuUbsSmIuhSwLAGhn
//    Gson    gson            = DrupalGsonFactory.getInstance().getGson();
//    Reader  data            = new InputStreamReader(GetWorkers.class.getResourceAsStream("nodes.json"), "UTF-8");
//    Type    resultListType  = new TypeToken<JsonEntityResultList<WittyRemark>>(){}.getType();
//
//    // Parse JSON to Java
//    JsonEntityResultList<WittyRemark> nodes = gson.fromJson(data, resultListType);
//
//    for (WittyRemark remark : nodes.getResults())
//    {
//      System.out.println(remark);
//    }
//
//    System.out.println(gson.toJson(nodes));
    SessionManager drupalSessionManager =
        new SessionManager(new URI("http://www.theredpool.com"), DRUPAL_USER_NAME, DRUPAL_PASSWORD);

    System.out.println(drupalSessionManager.getSessionToken());

    NodeRequestor requestor = new NodeRequestor(drupalSessionManager);
    Node          node      = requestor.requestNodeByNid(12);

    System.out.println(node);
//    System.out.println(DrupalGsonFactory.getInstance().createGson().toJson(node, Node.class));
//
//    List<Node> nodes = requestor.requestNodesByType("faq");
//
//    System.out.println(Arrays.toString(nodes.toArray()));

    node.setTitle(node.getTitle() + " CHANGED");

    requestor.updateNode(node);
  }
}