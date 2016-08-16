package pl.awislowski.elasticsearch.plugin.example;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.Table;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.cat.AbstractCatAction;
import org.elasticsearch.rest.action.support.RestTable;

import java.io.IOException;

import static org.elasticsearch.rest.RestRequest.Method.GET;

public class ExampleCatAction extends AbstractCatAction {

  @Inject
  public ExampleCatAction(Settings settings, RestController controller, Client client) {
    super(settings, controller, client);
    controller.registerHandler(GET, "_example", this);
  }

  @Override
  protected void doRequest(RestRequest request, RestChannel channel, Client client) {
    Table table = getTableWithHeader(request);
    table.startRow();
    table.addCell("Exampled");
    table.endRow();
    try {
      channel.sendResponse(RestTable.buildResponse(table,channel));
    } catch (Exception e) {
      try {
        channel.sendResponse(new BytesRestResponse(channel, e));
      } catch (IOException e1) {
        logger.error("failed to failure response", e1);
      }
    }
  }

  @Override
  protected void documentation(StringBuilder sb) {
    sb.append("_example\n");
  }

  @Override
  protected Table getTableWithHeader(RestRequest request) {
    final Table table = new Table();
    table.startHeaders();
    table.addCell("test", "desc:test");
    table.endHeaders();
    return table;
  }
}
