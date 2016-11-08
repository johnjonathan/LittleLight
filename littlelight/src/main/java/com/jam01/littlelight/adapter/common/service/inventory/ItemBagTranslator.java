package com.jam01.littlelight.adapter.common.service.inventory;

import com.bungie.netplatform.destiny.representation.Globals;
import com.bungie.netplatform.destiny.representation.ItemDefinition;
import com.bungie.netplatform.destiny.representation.ItemInstance;
import com.jam01.littlelight.domain.identityaccess.AccountId;
import com.jam01.littlelight.domain.inventory.Character;
import com.jam01.littlelight.domain.inventory.Item;
import com.jam01.littlelight.domain.inventory.ItemType;
import com.jam01.littlelight.domain.inventory.Vault;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jam01 on 7/26/16.
 */
public class ItemBagTranslator {
    public Character characterFrom(String characterId, List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        List<Item> items = new ArrayList<>(bungieDefinitions.size());
        items.addAll(transform(bungieDefinitions, bungieInstances));
        return new Character(characterId, items, accountId, characterId);
    }

    public Vault vaultFrom(List<ItemDefinition> bungieDefinitions, List<ItemInstance> bungieInstances, AccountId accountId) {
        List<Item> items = new ArrayList<>(bungieDefinitions.size());
        items.addAll(transform(bungieDefinitions, bungieInstances));
        return new Vault(accountId.withMembershipType() + accountId.withMembershipId(), items, accountId);
    }

    private Item transform(ItemDefinition definition, ItemInstance instance) {
        Item item = null;

        //We build our item if both params are != null and they represent the same item
        //TODO: consider using illegalArgumentExceptions
        if (instance != null && definition != null && (instance.getItemHash().equals(definition.getItemHash()))) {
            ItemType itemType = new ItemType(definition.getItemHash(), definition.getItemName(), definition.getBucketTypeHash(),
                    definition.getMaxStackSize().intValue(), "https://www.bungie.net" + definition.getIcon(), definition.getTierTypeName(),
                    Globals.classTypes.get(definition.getClassType()), definition.getEquippable(), Globals.buckets.get(definition.getBucketTypeHash()),
                    definition.getItemTypeName());

            Item.Builder builder = new Item.Builder(
                    instance.getItemInstanceId() + "-" + instance.getItemHash(),
                    instance.getItemInstanceId(),
                    itemType)
                    .stackSize(instance.getStackSize().intValue())
                    .isEquipped(instance.getIsEquipped())
                    .isGridComplete(instance.getIsGridComplete())
                    .damageType(Globals.damageTypes.get(instance.getDamageType().intValue()));

            // Some items do not have a primaryStat such as engrams or class items
            if (instance.getPrimaryStat() != null) {
                builder.damage(instance.getPrimaryStat().getValue().intValue())
                        .maxDamage(instance.getPrimaryStat().getMaximumValue().intValue());
            }

            // Setting Materials as itemType 10
            // See http://pastebin.com/Hy5ghZv1 line 552
//            if (definition.getBucketTypeHash().equals(3865314626L)) {
//                builder.itemType(10);
//            } else {
//                builder.itemType(definition.getItemType().intValue());
//            }

            item = builder.build();
        } else {
            System.out.println("Something went wrong!");
        }

        System.out.println(item.getItemName() + "|" + definition.getItemType());

        return item;
    }

//    private Item transform(ItemDefinition definition) {
//        Item item;
//        Item.Builder builder = new Item.Builder(
//                "-" + definition.getBungieItemHash(),
//                "",
//                definition.getBungieItemHash(),
//                definition.getItemName(),
//                definition.getBucketTypeHash())
//                .stackSize(definition.getMaxStackSize().intValue())
//                .maxStackSize(definition.getMaxStackSize().intValue())
//                .icon("https://www.bungie.net" + definition.getIcon())
//                .isEquipped(false)
//                .tierType(definition.getTierType().intValue())
//                .isGridComplete(true)
//                .classType(definition.getClassType().intValue());
//
//        // Setting Materials as itemType 10
//        // See http://pastebin.com/Hy5ghZv1 line 552
//        if (definition.getBucketTypeHash().equals(3865314626L)) {
//            builder.itemType(10);
//        } else {
//            builder.itemType(definition.getItemType().intValue());
//        }
//
//        item = builder.build();
//
//        return item;
//    }

    private Collection<Item> transform(List<ItemDefinition> definitions, List<ItemInstance> instances) {
        if (definitions.size() != instances.size()) {
            throw new IllegalArgumentException("Definition and Instance collections are not the same size");
        }
        Collection<Item> items = new ArrayList<>(definitions.size());
        for (int i = 0; i < instances.size(); i++) {
//            if (instances.get(i).getBungieItemHash().equals(2254123540L))
//                System.out.println("Debugging");
            items.add(transform(definitions.get(i), instances.get(i)));
        }
        return items;
    }

}
