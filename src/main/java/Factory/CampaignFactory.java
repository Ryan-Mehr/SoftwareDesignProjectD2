package Factory;

import Classes.PvE.Campaign;

public class CampaignFactory implements Interfaces.Campaign.CampaignFactory {
    @Override
    public Campaign createCampaign() {
        return new Campaign();
    }
}
