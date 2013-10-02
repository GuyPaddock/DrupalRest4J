import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

import com.redbottledesign.bitcoin.pool.drupal.WittyRemark;
import com.redbottledesign.bitcoin.pool.drupal.gson.requestor.WittyRemarkRequestor;
import com.redbottledesign.drupal.gson.SessionManager;
import com.redbottledesign.drupal.gson.exception.DrupalHttpException;

public class GetWorkers
{
  protected static final String DRUPAL_USER_NAME = "Pool manager daemon";
  protected static final String DRUPAL_PASSWORD  = "bxhaYDnrAVbAraeBPMIRhutcfDKIanv3AnIH7Skb83Jzci6yuUbsSmIuhSwLAGhn";

  public static void main(String[] args)
  throws URISyntaxException, IOException, DrupalHttpException, ParseException
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

//    System.out.println(drupalSessionManager.getSessionToken());
//
    WittyRemarkRequestor requestor = new WittyRemarkRequestor(drupalSessionManager);
    WittyRemark          node      = requestor.requestNodeByNid(11);
//
    System.out.println(node);
//    System.out.println(DrupalGsonFactory.getInstance().createGson().toJson(node, Node.class));
//
//    List<Node> nodes = requestor.requestNodesByType("faq");
//
//    System.out.println(Arrays.toString(nodes.toArray()));

//    node.setTitle(node.getTitle() + " CHANGED");
//
//    requestor.updateNode(node);

//    WittyRemark remark = new WittyRemark();
//
//    UserRequestor userRequestor       = new UserRequestor(drupalSessionManager);
//    User          poolManagementUser  = userRequestor.requestUserByUid(14);
//
//    System.out.println(poolManagementUser);
//
//    remark.setTitle("This is a fake remark.");
//    remark.setPublished(true);
//    remark.setAuthor(poolManagementUser.asReference());
//
//    requestor.createNode(remark);
//
//    System.out.println(remark);

//    RoundRequestor  roundRequestor = new RoundRequestor(drupalSessionManager);
//    Round           round          = roundRequestor.requestNodeByNid(26);
//
//    System.out.println(round);
//
//    round.setRoundStatus(Round.Status.OPEN);
//    round.getRoundDuration().setEndDate(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).parse("10/5/2013 6:35 AM"));
//
//    roundRequestor.updateNode(round);

//    SolvedBlockRequestor requestor2 = new SolvedBlockRequestor(drupalSessionManager);
//    SolvedBlock          block      = requestor2.requestNodeByNid(27);
//
//    System.out.println(block);

//    block.setCreationTime(new Date());
//    block.setReward(BigDecimal.valueOf(25));
//    block.setSolvingMember(new User.Reference(1));
//
//    requestor2.updateNode(block);
//
//    System.out.println(block);
  }
}