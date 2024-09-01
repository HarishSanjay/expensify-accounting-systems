<#-- Start of JSON Object -->
{
    "expenses": [
        <#-- Check if there are reports -->
        <#if reports?has_content>
            <#-- Create a variable to keep track of the current index -->
            <#assign first = true>
            <#-- Loop through each report -->
            <#list reports as report>
                <#-- Loop through each transaction in the report -->
                <#list report.transactionList as expense>
                    <#-- If not the first item, add a comma -->
                    <#if !first>
                        ,
                    </#if>
                    {
                        "vendorName": <#if expense.modifiedMerchant?has_content>"${expense.modifiedMerchant}"<#else>"${expense.merchant}"</#if>,
                        "amount": <#if expense.convertedAmount?has_content>${expense.convertedAmount/100}<#elseif expense.modifiedAmount?has_content>${expense.modifiedAmount/100}<#else>${expense.amount/100}</#if>,
                        "category": "${expense.category}",
                        "createdDate": <#if expense.modifiedCreated?has_content>"${expense.modifiedCreated}"<#else>"${expense.created}"</#if>,
                        "insertedAt": "${expense.inserted}",
                        "transactionId": "${expense.transactionID}"
                    }
                    <#-- Set the first flag to false after the first item -->
                    <#assign first = false>
                </#list>
            </#list>
        </#if>
    ]
}
